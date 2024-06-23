package com.pitachips.trxbatch.job.monthlyTrxReport;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReport;
import com.pitachips.trxbatch.dto.enums.ReportChannel;
import com.pitachips.trxbatch.exceptions.TrxBatchEmailServerProcessException;
import com.pitachips.trxbatch.repository.MonthlyTrxReportResultRepository;
import com.pitachips.trxbatch.service.email.MonthlyTrxReportBulkEmailService;
import com.pitachips.trxbatch.service.email.dto.BulkReserveMonthlyTrxReportRequestDto;
import com.pitachips.trxbatch.service.email.dto.BulkReserveMonthlyTrxReportRequestTemplateContent;
import com.pitachips.trxbatch.service.email.dto.BulkReserveResponseData;
import com.pitachips.trxbatch.service.email.dto.EmailServerResponse;
import com.pitachips.trxbatch.service.email.dto.enums.EmailServerResponseCode;
import com.pitachips.trxbatch.util.MaskUtil;

@Slf4j
@Component
@StepScope
public class MonthlyTrxReportViaEmailWriter implements ItemWriter<CustomerMonthlyTrxReport> {
    private static final int DAY_10 = 10;
    private static final int HOUR_9 = 9;
    private static final int MINUTE_00 = 0;

    private static final DateTimeFormatter TRANSACTIONED_AT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getIntegerInstance(Locale.ENGLISH);

    private final LocalDateTime sendAt;
    private final String targetYearMonthString;

    private final MonthlyTrxReportBulkEmailService monthlyTrxReportBulkEmailService;
    private final MonthlyTrxReportResultRepository monthlyTrxReportResultRepository;


    public MonthlyTrxReportViaEmailWriter(@Value("#{jobParameters['targetYearMonth']}") String targetYearMonthString,
                                          MonthlyTrxReportBulkEmailService monthlyTrxReportBulkEmailService,
                                          MonthlyTrxReportResultRepository monthlyTrxReportResultRepository) {
        this.sendAt = decideSendingDateTime(targetYearMonthString);
        this.targetYearMonthString = targetYearMonthString;
        this.monthlyTrxReportBulkEmailService = monthlyTrxReportBulkEmailService;
        this.monthlyTrxReportResultRepository = monthlyTrxReportResultRepository;
    }

    @Override
    public void write(Chunk<? extends CustomerMonthlyTrxReport> chunk) throws Exception {
        if (chunk.isEmpty()) {
            return;
        }

        var requestDto = new BulkReserveMonthlyTrxReportRequestDto();
        requestDto.setSendAt(sendAt);
        requestDto.setTemplateData(convertToMonthlyTrxReportTemplateData(chunk.getItems()));


        EmailServerResponse<BulkReserveResponseData> emailServerResponse =
                monthlyTrxReportBulkEmailService.requestBulkReserve(requestDto);

        if (emailServerResponse.getResponseCode() == EmailServerResponseCode.SUCCESS) {
            handleSuccess(chunk.getItems());
        } else {
            handleUnsuccesfulEmailServerProcess(chunk.getItems(), emailServerResponse, requestDto);
        }

    }

    private void handleSuccess(List<? extends CustomerMonthlyTrxReport> reports) {
        int i = monthlyTrxReportResultRepository.batchInsertSuccessMonthlyTrxReportResult(reports.stream()
                                                                                                 .map(CustomerMonthlyTrxReport::getCustomerId)
                                                                                                 .collect(Collectors.toList()),
                                                                                          YearMonth.parse(targetYearMonthString),
                                                                                          ReportChannel.EMAIL);
        log.info("Inserted {} success records to monthlyTrxReportResultRepository", i);
    }

    private void handleUnsuccesfulEmailServerProcess(List<? extends CustomerMonthlyTrxReport> items,
                                                     EmailServerResponse<BulkReserveResponseData> emailServerResponse,
                                                     BulkReserveMonthlyTrxReportRequestDto requestDto) {

        log.error("E-mail server returned {}. Failure will be recorded to DB of data: {}",
                  emailServerResponse.getResponseCode(),
                  requestDto);

        int i = monthlyTrxReportResultRepository.batchInsertFailMonthlyTrxReportResult(items.stream()
                                                                                            .map(CustomerMonthlyTrxReport::getCustomerId)
                                                                                            .collect(Collectors.toList()),
                                                                                       YearMonth.parse(targetYearMonthString),
                                                                                       ReportChannel.EMAIL,
                                                                                       new TrxBatchEmailServerProcessException(
                                                                                               emailServerResponse.getResponseMessage()));

        log.info("Inserted {} fail records to monthlyTrxReportResultRepository", i);

    }


    private LinkedHashMap<Long, BulkReserveMonthlyTrxReportRequestTemplateContent> convertToMonthlyTrxReportTemplateData(List<?
            extends CustomerMonthlyTrxReport> reports) {

        LinkedHashMap<Long, BulkReserveMonthlyTrxReportRequestTemplateContent> templateData = new LinkedHashMap<>();
        reports.forEach(report -> {
            var templateContent = new BulkReserveMonthlyTrxReportRequestTemplateContent();
            templateContent.setMonthlyTrxReportRows(report.getCustomerMonthlyTrxReportDetails().stream().map(vo -> {
                var row = new BulkReserveMonthlyTrxReportRequestTemplateContent.MonthlyTrxReportRow();
                row.setAcctNo(MaskUtil.maskTailWithAsterisk(vo.getAcctNo(), 2));
                row.setTickerNameKr(vo.getTickerNameKr());
                row.setTransactionedAt(vo.getTransactionedAt().format(TRANSACTIONED_AT_FORMATTER));
                row.setType(vo.getType().name());
                row.setPrice(NUMBER_FORMAT.format(vo.getPrice()));
                row.setQuantity(NUMBER_FORMAT.format(vo.getQuantity()));
                row.setAmount(NUMBER_FORMAT.format(vo.getAmount()));
                row.setCurrency(vo.getCurrency());
                return row;
            }).collect(Collectors.toList()));
            templateData.putIfAbsent(report.getCustomerId(), templateContent);
        });
        return templateData;
    }


    private static LocalDateTime decideSendingDateTime(String targetYearMonthString) {
        return YearMonth.parse(targetYearMonthString).plusMonths(1).atDay(DAY_10).atTime(HOUR_9, MINUTE_00);
    }

}
