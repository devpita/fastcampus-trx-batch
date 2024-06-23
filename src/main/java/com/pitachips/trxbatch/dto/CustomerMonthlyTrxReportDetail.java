package com.pitachips.trxbatch.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.pitachips.trxbatch.dto.enums.TransactionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMonthlyTrxReportDetail {
    private String acctNo;
    private String tickerNameKr;
    private LocalDateTime transactionedAt;
    private TransactionType type;
    private long price;
    private long quantity;
    private BigInteger amount;
    private String currency;
}
