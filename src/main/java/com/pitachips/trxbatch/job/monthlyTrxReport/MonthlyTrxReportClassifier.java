package com.pitachips.trxbatch.job.monthlyTrxReport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReport;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyTrxReportClassifier implements
        Classifier<CustomerMonthlyTrxReport, ItemWriter<? super CustomerMonthlyTrxReport>> {


    @Override
    public ItemWriter<? super CustomerMonthlyTrxReport> classify(CustomerMonthlyTrxReport customerMonthlyTrxReport) {
        return null;
    }
}
