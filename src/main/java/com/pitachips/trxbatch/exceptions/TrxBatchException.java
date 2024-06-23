package com.pitachips.trxbatch.exceptions;

import lombok.Getter;

import com.pitachips.trxbatch.exceptions.enums.TrxBatchExceptionCode;

@Getter
public class TrxBatchException extends RuntimeException {
    private final TrxBatchExceptionCode exceptionCode;

    public TrxBatchException(TrxBatchExceptionCode exceptionCode) {
        super();
        this.exceptionCode = exceptionCode;
    }

    public TrxBatchException(TrxBatchExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public TrxBatchException(TrxBatchExceptionCode exceptionCode, Throwable cause) {
        super(cause);
        this.exceptionCode = exceptionCode;
    }

}
