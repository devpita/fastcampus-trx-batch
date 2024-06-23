package com.pitachips.trxbatch.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.pitachips.trxbatch.controller.dto.enums.JobControllerResponseCode;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobControllerResponse {
    private JobControllerResponseCode responseCode;
    private String message;
}
