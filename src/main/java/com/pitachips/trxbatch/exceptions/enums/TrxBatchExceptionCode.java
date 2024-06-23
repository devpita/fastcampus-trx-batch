package com.pitachips.trxbatch.exceptions.enums;

import lombok.Getter;

@Getter
public enum TrxBatchExceptionCode {
    UNEXPECTED("UNEXPECTED"),
    NOT_SUPPORTED("NOT_SUPPORTED");


    private final String dbCode;

    TrxBatchExceptionCode(String dbCode) {
        this.dbCode = dbCode;
    }

}
