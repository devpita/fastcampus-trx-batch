package com.pitachips.trxbatch.job.monthlyTrxReport;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReport;

@Slf4j
@Component
@StepScope
public class MonthlyTrxReportPagingItemReader extends AbstractPagingItemReader<CustomerMonthlyTrxReport> {

    public MonthlyTrxReportPagingItemReader(@Value("#{jobParameters['targetYearMonth']}") String targetYearMonthString) {
        // TODO: do something
    }

    @Override
    protected void doReadPage() {
        // TODO: do something
        this.results = new ArrayList<>();
    }
}
