package com.pitachips.trxbatch.job.monthlyTrxReport;

import java.time.YearMonth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pitachips.trxbatch.repository.MonthlyTrxHeavyCustomerRepository;

import static com.pitachips.trxbatch.job.monthlyTrxReport.MonthlyTrxReportJobConfiguration.JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION;

@Slf4j
@Component
@RequiredArgsConstructor
@StepScope
public class HeavyCustomerSeparationItemWriter implements ItemWriter<Long> {

    private final MonthlyTrxHeavyCustomerRepository monthlyTrxHeavyCustomerRepository;

    private YearMonth trxYearMonth;

    @Value(JOB_PARAM_TARGET_YEAR_MONTH_EXPRESSION)
    public void setTrxYearMonth(String trxYearMonthString) {
        this.trxYearMonth = YearMonth.parse(trxYearMonthString);
    }

    @Override
    public void write(Chunk<? extends Long> chunk) throws Exception {
        monthlyTrxHeavyCustomerRepository.batchInsert(chunk.getItems(), trxYearMonth);
    }
}
