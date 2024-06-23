package com.pitachips.trxbatch.job.monthlyTrxReport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReport;
import com.pitachips.trxbatch.exceptions.TrxBatchNotSupportedException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyTrxReportClassifier implements
        Classifier<CustomerMonthlyTrxReport, ItemWriter<? super CustomerMonthlyTrxReport>> {

    private final MonthlyTrxReportViaEmailWriter monthlyTrxReportViaEmailWriter;
    private final MonthlyTrxReportViaPostWriter monthlyTrxReportViaPostWriter;
    private final MonthlyTrxReportViaAppMessengerWriter monthlyTrxReportViaAppMessengerWriter;

    @Override
    public ItemWriter<? super CustomerMonthlyTrxReport> classify(CustomerMonthlyTrxReport customerMonthlyTrxReport) {
        return switch (customerMonthlyTrxReport.getChannel()) {
            case APP_MESSAGE -> monthlyTrxReportViaAppMessengerWriter;
            case EMAIL -> monthlyTrxReportViaEmailWriter;
            case POST -> monthlyTrxReportViaPostWriter;
            default -> throw new TrxBatchNotSupportedException("Unsupported channel: " + customerMonthlyTrxReport.getChannel());
        };
    }
}
