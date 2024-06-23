package com.pitachips.trxbatch.job.monthlyTrxReport;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReport;
import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReportDetail;
import com.pitachips.trxbatch.dto.MonthlyTrxSummary;
import com.pitachips.trxbatch.dto.enums.ReportChannel;
import com.pitachips.trxbatch.exceptions.TrxBatchUnexpectedException;
import com.pitachips.trxbatch.repository.AppMessageRepository;
import com.pitachips.trxbatch.repository.MonthlyTrxReportResultRepository;

@Slf4j
@Component
@StepScope
public class MonthlyTrxReportViaAppMessengerWriter implements ItemWriter<CustomerMonthlyTrxReport> {
    private static final int DAY_10 = 10;
    private final int year;
    private final int month;
    private final LocalDate sendDate;
    private final AppMessageRepository appMessageRepository;
    private final MonthlyTrxReportResultRepository monthlyTrxReportResultRepository;


    public MonthlyTrxReportViaAppMessengerWriter(@Value("#{jobParameters['targetYearMonth']}") String targetYearMonthString,
                                                 AppMessageRepository appMessageRepository,
                                                 MonthlyTrxReportResultRepository monthlyTrxReportResultRepository) {
        YearMonth targetYearMonth = YearMonth.parse(targetYearMonthString);
        this.year = targetYearMonth.getYear();
        this.month = targetYearMonth.getMonthValue();
        this.sendDate = targetYearMonth.plusMonths(1).atDay(DAY_10);
        this.appMessageRepository = appMessageRepository;
        this.monthlyTrxReportResultRepository = monthlyTrxReportResultRepository;
    }

    @Override
    public void write(Chunk<? extends CustomerMonthlyTrxReport> reports) throws Exception {
        if (reports.isEmpty()) {
            return;
        }

        List<MonthlyTrxSummary> summaries = reports.getItems().stream().map(report -> {
            List<CustomerMonthlyTrxReportDetail> details = report.getCustomerMonthlyTrxReportDetails();
            return new MonthlyTrxSummary(report.getCustomerId(),
                                         details.size(),
                                         details.stream()
                                                .map(CustomerMonthlyTrxReportDetail::getAmount)
                                                .reduce(BigInteger.ZERO, BigInteger::add));
        }).toList();

        try {
            appMessageRepository.batchInsertMonthlyTrxReport(year, month, sendDate, summaries);
        } catch (Exception e) {
            log.error("Failed to send monthly transaction report to App Messenger. count={}", summaries, e);
            handleException(summaries, e);
            return;
        }

        handleSuccess(summaries);
    }

    private void handleSuccess(List<MonthlyTrxSummary> summaries) {
        log.info("Monthly transaction report has been successfully sent to App Messenger. summaries={}", summaries);

        int i = monthlyTrxReportResultRepository.batchInsertSuccessMonthlyTrxReportResult(summaries.stream()
                                                                                                   .map(MonthlyTrxSummary::getCustomerId)
                                                                                                   .toList(),
                                                                                          YearMonth.of(year, month),
                                                                                          ReportChannel.APP_MESSAGE);
        log.info("Inserted {} success records to monthlyTrxReportResultRepository", i);
    }

    private void handleException(List<MonthlyTrxSummary> summaries, Exception e) {
        int i = monthlyTrxReportResultRepository.batchInsertFailMonthlyTrxReportResult(summaries.stream()
                                                                                                .map(MonthlyTrxSummary::getCustomerId)
                                                                                                .toList(),
                                                                                       YearMonth.of(year, month),
                                                                                       ReportChannel.APP_MESSAGE,
                                                                                       new TrxBatchUnexpectedException(e));
        log.info("Inserted {} fail records to monthlyTrxReportResultRepository", i);
    }

}
