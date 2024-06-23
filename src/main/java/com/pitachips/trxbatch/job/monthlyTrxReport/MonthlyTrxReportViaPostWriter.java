package com.pitachips.trxbatch.job.monthlyTrxReport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReport;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyTrxReportViaPostWriter implements ItemWriter<CustomerMonthlyTrxReport> {

    public MonthlyTrxReportViaPostWriter(@Value("#{jobParameters['targetYearMonth']}") String targetYearMonthString) {
        // TODO: do something
    }

    @Override
    public void write(Chunk<? extends CustomerMonthlyTrxReport> chunk) throws Exception {
        // TODO: do something
    }
}
