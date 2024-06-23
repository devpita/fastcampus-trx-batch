package com.pitachips.trxbatch.job.monthlyTrxReport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.pitachips.trxbatch.dto.CustomerMonthlyTrxReport;
import com.pitachips.trxbatch.dto.enums.ReportChannel;
import com.pitachips.trxbatch.exceptions.TrxBatchNotSupportedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MonthlyTrxReportClassifierUnitTest {

    @Mock
    private MonthlyTrxReportViaEmailWriter emailWriter;

    @Mock
    private MonthlyTrxReportViaPostWriter postWriter;

    @Mock
    private MonthlyTrxReportViaAppMessengerWriter appMessengerWriter;

    private MonthlyTrxReportClassifier classifier;

    @BeforeEach
    void setUp() {
        classifier = new MonthlyTrxReportClassifier(emailWriter, postWriter, appMessengerWriter);
    }

    @Test
    public void testClassify() {
        // when
        var emailReport = new CustomerMonthlyTrxReport();
        emailReport.setChannel(ReportChannel.EMAIL);
        var emailWriterResult = classifier.classify(emailReport);

        var postReport = new CustomerMonthlyTrxReport();
        postReport.setChannel(ReportChannel.POST);
        var postWriterResult = classifier.classify(postReport);

        var appMessengerReport = new CustomerMonthlyTrxReport();
        appMessengerReport.setChannel(ReportChannel.APP_MESSAGE);
        var appMessengerWriterResult = classifier.classify(appMessengerReport);

        // then
        assertEquals(emailWriter, emailWriterResult);
        assertEquals(postWriter, postWriterResult);
        assertEquals(appMessengerWriter, appMessengerWriterResult);
    }

    @Test
    public void testClassifyWithUnknownChannel() {
        // given
        var report = new CustomerMonthlyTrxReport();
        report.setChannel(ReportChannel.NONE);

        // expect
        assertThrows(TrxBatchNotSupportedException.class, () -> classifier.classify(report));
    }
}
