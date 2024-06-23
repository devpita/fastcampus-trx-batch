package com.pitachips.trxbatch.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.pitachips.trxbatch.exceptions.TrxBatchEmailServerCommunicationException;
import com.pitachips.trxbatch.service.email.dto.BulkReserveMonthlyTrxReportRequestDto;
import com.pitachips.trxbatch.service.email.dto.BulkReserveResponseData;
import com.pitachips.trxbatch.service.email.dto.EmailServerResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyTrxReportBulkEmailService {

    private final RestClient emailServerRestClient;

    public EmailServerResponse<BulkReserveResponseData> requestBulkReserve(BulkReserveMonthlyTrxReportRequestDto requestDto)
            throws TrxBatchEmailServerCommunicationException {

        return this.emailServerRestClient.post()
                                         .uri("/bulk-reserve")
                                         .body(requestDto)
                                         .retrieve()
                                         .onStatus(HttpStatusCode::isError, (request, response) -> {
                                             throw new TrxBatchEmailServerCommunicationException(
                                                     "Failed to request bulk reserve email");
                                         })
                                         .body(new ParameterizedTypeReference<>() {
                                         });
    }

}
