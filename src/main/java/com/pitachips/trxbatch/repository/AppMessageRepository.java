package com.pitachips.trxbatch.repository;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import com.pitachips.trxbatch.dto.MonthlyTrxSummary;

import static com.pitachips.trxbatch.generated.Tables.APP_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AppMessageRepository {

    private static final String INITIAL_STATUS = "CREATED";

    private static final long MONTHLY_TRX_REPORT_TEMPLATED_ID = 245L;

    private final DSLContext trxBatchDsl;


    public int batchInsertMonthlyTrxReport(int year, int month, LocalDate sendDate, List<MonthlyTrxSummary> summaries) {

        return trxBatchDsl.insertInto(APP_MESSAGE)
                          .columns(APP_MESSAGE.CUSTOMER_ID,
                                   APP_MESSAGE.TEMPLATE_ID,
                                   APP_MESSAGE.SEND_DATE,
                                   APP_MESSAGE.STATUS,
                                   APP_MESSAGE.VALUE1,
                                   APP_MESSAGE.VALUE2,
                                   APP_MESSAGE.VALUE3,
                                   APP_MESSAGE.VALUE4)
                          .valuesOfRows(summaries.stream()
                                                 .map(summary -> DSL.row(summary.getCustomerId(),
                                                                         MONTHLY_TRX_REPORT_TEMPLATED_ID,
                                                                         sendDate,
                                                                         INITIAL_STATUS,
                                                                         String.valueOf(year),
                                                                         String.valueOf(month),
                                                                         String.valueOf(summary.getTrxCount()),
                                                                         String.valueOf(summary.getTrxAmountSum())))
                                                 .toList())
                          .execute();

    }

}
