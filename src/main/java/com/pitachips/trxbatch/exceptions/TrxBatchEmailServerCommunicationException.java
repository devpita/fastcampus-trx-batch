package com.pitachips.trxbatch.exceptions;

import com.pitachips.trxbatch.exceptions.enums.TrxBatchExceptionCode;

public class TrxBatchEmailServerCommunicationException extends TrxBatchException {

    private static final TrxBatchExceptionCode CODE = TrxBatchExceptionCode.EMAIL_SERVER_COMMUNICATION_ERROR;

    public TrxBatchEmailServerCommunicationException() {
        super(CODE);
    }

    public TrxBatchEmailServerCommunicationException(String message) {
        super(CODE, message);
    }
}
