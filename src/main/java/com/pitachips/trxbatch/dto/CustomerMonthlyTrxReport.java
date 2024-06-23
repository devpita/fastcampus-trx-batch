package com.pitachips.trxbatch.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.pitachips.trxbatch.dto.enums.ReportChannel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMonthlyTrxReport {
    private Long customerId;
    private ReportChannel channel;
    private List<CustomerMonthlyTrxReportDetail> customerMonthlyTrxReportDetails;
}
