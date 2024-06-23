package com.pitachips.trxbatch.job.monthlyTrxReport;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReport;
import com.pitachips.trxbatch.repository.MonthlyTrxReportRepository;

@Slf4j
@Component
@StepScope
public class MonthlyTrxReportPagingItemReader extends AbstractPagingItemReader<CustomerMonthlyTrxReport> {

    private static final int DEFAULT_PAGE_SIZE = 100;

    private final MonthlyTrxReportRepository monthlyTrxReportRepository;
    private final LocalDateTime from; // inclusive
    private final LocalDateTime before; // exclusive

    private Long lastCustomerId;

    public MonthlyTrxReportPagingItemReader(@Value("#{jobParameters['targetYearMonth']}") String targetYearMonthString,
                                            MonthlyTrxReportRepository monthlyTrxReportRepository) {
        YearMonth targetYearMonth = YearMonth.parse(targetYearMonthString);
        this.from = targetYearMonth.atDay(1).atStartOfDay();
        this.before = targetYearMonth.plusMonths(1).atDay(1).atStartOfDay();
        this.monthlyTrxReportRepository = monthlyTrxReportRepository;
        this.setPageSize(DEFAULT_PAGE_SIZE);
    }

    @Override
    protected void doReadPage() {
        if (results ==null ) {
            results = new CopyOnWriteArrayList<>();
        } else {
            results.clear();
        }

        List<CustomerMonthlyTrxReport> customerMonthlyTrxReports =
                monthlyTrxReportRepository.fetchCustomerMonthlyTrxReports(from, before, lastCustomerId, DEFAULT_PAGE_SIZE);

        if (!customerMonthlyTrxReports.isEmpty()) {
            lastCustomerId = customerMonthlyTrxReports.getLast().getCustomerId();
        }

        this.results.addAll(customerMonthlyTrxReports);

    }
}
