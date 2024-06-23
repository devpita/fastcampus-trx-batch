package com.pitachips.trxbatch.dto;

import java.math.BigInteger;

import lombok.Data;

@Data
public class MonthlyTrxSummary {
    private final Long customerId;
    private final int trxCount;
    private final BigInteger trxAmountSum;
}
