package com.pitachips.trxbatch.exceptions.enums;

import lombok.Getter;

@Getter
public enum TrxBatchExceptionCode {
    UNEXPECTED("UNEXPECTED"),
    NOT_SUPPORTED("NOT_SUPPORTED"),
    INVALID_ARGUMENT("INVALID_ARG"),
    EMAIL_SERVER_COMMUNICATION_ERROR("EMAIL_SERV_COMM_ERR"),
    ;


    private final String dbCode;

    TrxBatchExceptionCode(String dbCode) {
        this.dbCode = dbCode;
    }

}
