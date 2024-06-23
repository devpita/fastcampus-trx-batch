package com.pitachips.trxbatch.exceptions;

import com.pitachips.trxbatch.exceptions.enums.TrxBatchExceptionCode;

public class TrxBatchUnexpectedException extends TrxBatchException {

    private static final TrxBatchExceptionCode CODE = TrxBatchExceptionCode.UNEXPECTED;

    public TrxBatchUnexpectedException() {
        super(CODE);
    }

    public TrxBatchUnexpectedException(String message) {
        super(CODE, message);
    }

    public TrxBatchUnexpectedException(Throwable cause) {
        super(CODE, cause);
    }
}
