package com.pitachips.trxbatch.job.monthlyTrxReport.testhelper;

import java.time.LocalDate;

import org.jooq.DSLContext;

import com.pitachips.trxbatch.dto.enums.ReportChannel;

import static com.pitachips.trxbatch.generated.Tables.APP_MESSAGE;
import static com.pitachips.trxbatch.generated.Tables.MONTHLY_TRX_REPORT_RESULT;
import static org.jooq.impl.DSL.count;

public class MonthlyTrxReportJobDbIntgTestDataVerificationHelper {

    public static final String TARGET_YEAR_MONTH = "2024-04";

    public static final Integer SUCCESSFUL_REPORT_SUCCESS_COUNT = 439;
    public static final Integer SUCCESSFUL_REPORT_VIA_POST_SUCCESS_COUNT = 143;
    public static final Integer SUCCESSFUL_REPORT_VIA_EMAIL_SUCCESS_COUNT = 150;
    public static final Integer SUCCESSFUL_REPORT_VIA_APP_MESSAGE_SUCCESS_COUNT = 146;
    public static final Integer SUCCESSFUL_APP_MESSAGE_DB_STORE_COUNT = SUCCESSFUL_REPORT_VIA_APP_MESSAGE_SUCCESS_COUNT;


    public static Integer getReportSuccessCount(DSLContext trxBatchhDsl) {
        return trxBatchhDsl.select(count())
                           .from(MONTHLY_TRX_REPORT_RESULT)
                           .where(MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH.eq(TARGET_YEAR_MONTH))
                           .and(MONTHLY_TRX_REPORT_RESULT.STATUS.eq("SUCCESS"))
                           .fetchOne()
                           .value1();
    }

    public static Integer getReportViaPostSuccessCount(DSLContext trxBatchhDsl) {
        return trxBatchhDsl.select(count())
                           .from(MONTHLY_TRX_REPORT_RESULT)
                           .where(MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH.eq(TARGET_YEAR_MONTH))
                           .and(MONTHLY_TRX_REPORT_RESULT.STATUS.eq("SUCCESS"))
                           .and(MONTHLY_TRX_REPORT_RESULT.CHANNEL.eq(ReportChannel.POST.name()))
                           .fetchOne()
                           .value1();
    }

    public static Integer getReportViaEmailSuccessCount(DSLContext trxBatchhDsl) {
        return trxBatchhDsl.select(count())
                           .from(MONTHLY_TRX_REPORT_RESULT)
                           .where(MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH.eq(TARGET_YEAR_MONTH))
                           .and(MONTHLY_TRX_REPORT_RESULT.STATUS.eq("SUCCESS"))
                           .and(MONTHLY_TRX_REPORT_RESULT.CHANNEL.eq(ReportChannel.EMAIL.name()))
                           .fetchOne()
                           .value1();
    }

    public static Integer getReportViaAppMessageSuccessCount(DSLContext trxBatchhDsl) {
        return trxBatchhDsl.select(count())
                           .from(MONTHLY_TRX_REPORT_RESULT)
                           .where(MONTHLY_TRX_REPORT_RESULT.TARGET_YEAR_MONTH.eq(TARGET_YEAR_MONTH))
                           .and(MONTHLY_TRX_REPORT_RESULT.STATUS.eq("SUCCESS"))
                           .and(MONTHLY_TRX_REPORT_RESULT.CHANNEL.eq(ReportChannel.APP_MESSAGE.name()))
                           .fetchOne()
                           .value1();
    }

    public static Integer getAppMessageDbStoredCount(DSLContext trxBatchhDsl) {
        return trxBatchhDsl.select(count())
                           .from(APP_MESSAGE)
                           .where(APP_MESSAGE.SEND_DATE.eq(LocalDate.of(2024, 5, 10)))
                           .fetchOne()
                           .value1();
    }


}
