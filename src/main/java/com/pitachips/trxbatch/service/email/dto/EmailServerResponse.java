package com.pitachips.trxbatch.service.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.pitachips.trxbatch.service.email.dto.enums.EmailServerResponseCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailServerResponse<T> {
    private EmailServerResponseCode responseCode;
    private String responseMessage;
    private T data;
}
