package com.pitachips.trxbatch.repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.pitachips.trxbatch.dto.MonthlyTrxSummary;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class AppMessageRepositoryDbIntgTest {

    @Autowired
    private AppMessageRepository appMessageRepository;

    @Test
    void batchInsertMonthlyTrxReportSuccess() {
        // Given
        LocalDate sendDate = LocalDate.of(2024, 6, 10);
        List<MonthlyTrxSummary> summaries = List.of(new MonthlyTrxSummary(1L, 10, BigInteger.valueOf(1000)),
                                                    new MonthlyTrxSummary(2L, 15, BigInteger.valueOf(1500)));

        // When
        int inserted = appMessageRepository.batchInsertMonthlyTrxReport(2024, 5, sendDate, summaries);

        // Then
        assertEquals(2, inserted);
    }

}
