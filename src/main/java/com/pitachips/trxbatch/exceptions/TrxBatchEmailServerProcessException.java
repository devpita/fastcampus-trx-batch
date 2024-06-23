package com.pitachips.trxbatch.exceptions;

import com.pitachips.trxbatch.exceptions.enums.TrxBatchExceptionCode;

public class TrxBatchEmailServerProcessException extends TrxBatchException {

    private static final TrxBatchExceptionCode CODE = TrxBatchExceptionCode.EMAIL_SERVER_PROCESS_ERROR;

    public TrxBatchEmailServerProcessException() {
        super(CODE);
    }

    public TrxBatchEmailServerProcessException(String message) {
        super(CODE, message);
    }
}
