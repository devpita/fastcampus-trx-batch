package com.pitachips.trxbatch.exceptions.enums;

import lombok.Getter;

@Getter
public enum TrxBatchExceptionCode {
    UNEXPECTED("UNEXPECTED"),
    NOT_SUPPORTED("NOT_SUPPORTED"),
    INVALID_ARGUMENT("INVALID_ARG"),
    EMAIL_SERVER_COMMUNICATION_ERROR("EMAIL_SERV_COMM_ERR"),
    EMAIL_SERVER_PROCESS_ERROR("EMAIL_SERV_PROC_ERR"),
    CSV_WRITE_ERROR("CSV_WRITE_ERR"),
    ;


    private final String dbCode;

    TrxBatchExceptionCode(String dbCode) {
        this.dbCode = dbCode;
    }

}
