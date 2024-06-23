package com.pitachips.trxbatch.job.monthlyTrxReport.testhelper;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.pitachips.trxbatch.generated.Tables.APP_MESSAGE;
import static com.pitachips.trxbatch.generated.Tables.MONTHLY_TRX_REPORT_RESULT;

@Component
@Profile("dbtest")
public class MonthlyTrxReportDataCleaner {

    @Autowired
    private final DSLContext trxBatchDsl;

    public MonthlyTrxReportDataCleaner(DSLContext trxBatchDsl) {
        this.trxBatchDsl = trxBatchDsl;
    }

    public void deleteAllApplicationDataCreatedDuringJobExecution() {
        trxBatchDsl.deleteFrom(MONTHLY_TRX_REPORT_RESULT).execute();
        trxBatchDsl.deleteFrom(APP_MESSAGE).execute();
    }
}
