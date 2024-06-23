package com.pitachips.trxbatch.repository;


import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReport;
import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReportDetail;
import com.pitachips.trxbatch.dto.enums.CustomerCommType;
import com.pitachips.trxbatch.dto.enums.ReportChannel;

import static com.pitachips.trxbatch.generated.Tables.*;
import static com.pitachips.trxbatch.repository.MonthlyTrxHeavyCustomerRepository.YEAR_MONTH_FORMATTER;
import static org.jooq.impl.DSL.noCondition;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MonthlyTrxReportRepository {

    private final DSLContext trxBatchDsl;

    public List<CustomerMonthlyTrxReport> fetchCustomerMonthlyTrxReports(LocalDateTime from,  // inclusive
                                                                         LocalDateTime before, // exclusive
                                                                         Long lastCustomerId, int limit) {

        String trxYearMonth = YEAR_MONTH_FORMATTER.format(YearMonth.of(from.getYear(), from.getMonthValue()));

        Table<Record2<Long, String>> pagedDrivingTable = trxBatchDsl.select(CUSTOMER_COMM.CUSTOMER_ID, CUSTOMER_COMM.CHANNEL)
                                                                    .from(CUSTOMER_COMM)
                                                                    .where(CUSTOMER_COMM.TYPE.eq(CustomerCommType.MONTHLY_TRX_REPORT.name()))
                                                                    .and(CUSTOMER_COMM.CHANNEL.in(ReportChannel.POST.name(),
                                                                                                  ReportChannel.APP_MESSAGE.name(),
                                                                                                  ReportChannel.EMAIL.name()))
                                                                    .and(CUSTOMER_COMM.CUSTOMER_ID.notIn(
                                                                            DSL.select(MONTHLY_TRX_HEAVY_CUSTOMER.CUSTOMER_ID)
                                                                               .from(MONTHLY_TRX_HEAVY_CUSTOMER)
                                                                               .where(MONTHLY_TRX_HEAVY_CUSTOMER.TRX_YEAR_MONTH.eq(trxYearMonth))))
                                                                    .and(lastCustomerId != null ? CUSTOMER_COMM.CUSTOMER_ID.gt(
                                                                            lastCustomerId) : noCondition())
                                                                    .and(CUSTOMER_COMM.CUSTOMER_ID.in(DSL.select(ACCOUNT.CUSTOMER_ID)
                                                                                                    .from(ACCOUNT)
                                                                                                    .join(TRX)
                                                                                                    .on(ACCOUNT.ACCT_NO.eq(TRX.ACCT_NO))
                                                                                                    .where(TRX.TRANSACTIONED_AT.ge(from))
                                                                                                    .and(TRX.TRANSACTIONED_AT.lt(before))))
                                                                    .orderBy(CUSTOMER_COMM.CUSTOMER_ID)
                                                                    .limit(limit)
                                                                    .asTable();

        Map<Record, List<CustomerMonthlyTrxReportDetail>> joined =
                trxBatchDsl.select(pagedDrivingTable.field(CUSTOMER_COMM.CUSTOMER_ID),
                                   pagedDrivingTable.field(CUSTOMER_COMM.CHANNEL),
                                   TRX.ACCT_NO,
                                   TRX.TICKER_NAME_KR,
                                   TRX.TRANSACTIONED_AT,
                                   TRX.TYPE,
                                   TRX.PRICE,
                                   TRX.QUANTITY,
                                   TRX.PRICE.multiply(TRX.QUANTITY).as("amount"),
                                   TRX.CURRENCY)
                           .from(TRX)
                           .join(ACCOUNT)
                           .on(TRX.ACCT_NO.eq(ACCOUNT.ACCT_NO))
                           .join(pagedDrivingTable)
                           .on(ACCOUNT.CUSTOMER_ID.eq(pagedDrivingTable.field(CUSTOMER_COMM.CUSTOMER_ID)))
                           .where(TRX.TRANSACTIONED_AT.ge(from))
                           .and(TRX.TRANSACTIONED_AT.lt(before))
                           .orderBy(pagedDrivingTable.field(CUSTOMER_COMM.CUSTOMER_ID),
                                    TRX.TRANSACTIONED_AT.desc(),
                                    TRX.ID.desc())
                           .fetchGroups(pagedDrivingTable.fields(), CustomerMonthlyTrxReportDetail.class);

        return joined.entrySet().stream().map(entry -> {
            CustomerMonthlyTrxReport report = new CustomerMonthlyTrxReport();
            report.setCustomerId(entry.getKey().get(CUSTOMER_COMM.CUSTOMER_ID));
            report.setChannel(ReportChannel.valueOf(entry.getKey().get(CUSTOMER_COMM.CHANNEL)));
            report.setCustomerMonthlyTrxReportDetails(entry.getValue());
            return report;
        }).toList();

    }


    public List<Long> fetchHeavyCustomerIds(LocalDateTime from,  // inclusive
                                            LocalDateTime before,  // exclusive
                                            int heavyCustomerThreshold, Long lastCustomerId, int limit) {

        return trxBatchDsl.select(CUSTOMER_COMM.CUSTOMER_ID)
                          .from(TRX)
                          .join(ACCOUNT)
                          .on(TRX.ACCT_NO.eq(ACCOUNT.ACCT_NO))
                          .join(CUSTOMER_COMM)
                          .on(ACCOUNT.CUSTOMER_ID.eq(CUSTOMER_COMM.CUSTOMER_ID))
                          .where(TRX.TRANSACTIONED_AT.ge(from))
                          .and(TRX.TRANSACTIONED_AT.lt(before))
                          .and(lastCustomerId != null ? CUSTOMER_COMM.CUSTOMER_ID.gt(lastCustomerId) : noCondition())
                          .and(CUSTOMER_COMM.TYPE.eq(CustomerCommType.MONTHLY_TRX_REPORT.name()))
                          .groupBy(CUSTOMER_COMM.CUSTOMER_ID)
                          .having(DSL.count(TRX.ID).gt(heavyCustomerThreshold))
                          .orderBy(CUSTOMER_COMM.CUSTOMER_ID)
                          .limit(limit)
                          .fetchInto(Long.class);
    }
}
