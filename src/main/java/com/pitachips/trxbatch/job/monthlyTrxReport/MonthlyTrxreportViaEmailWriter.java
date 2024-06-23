package com.pitachips.trxbatch.job.monthlyTrxReport;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReport;

public class MonthlyTrxreportViaEmailWriter implements ItemWriter<CustomerMonthlyTrxReport> {

    public MonthlyTrxreportViaEmailWriter(@Value("#{jobParameters['targetYearMonth']}") String targetYearMonthString) {
        // TODO: do something
    }

    @Override
    public void write(Chunk<? extends CustomerMonthlyTrxReport> chunk) throws Exception {
        // TODO: do something
    }
}