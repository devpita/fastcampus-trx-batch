package com.pitachips.trxbatch.exceptions;

import com.pitachips.trxbatch.exceptions.enums.TrxBatchExceptionCode;

public class TrxBatchCsvWriteException extends TrxBatchException {

    private static final TrxBatchExceptionCode CODE = TrxBatchExceptionCode.CSV_WRITE_ERROR;

    public TrxBatchCsvWriteException() {
        super(CODE);
    }

    public TrxBatchCsvWriteException(String message) {
        super(CODE, message);
    }

    public TrxBatchCsvWriteException(Throwable cause) {
        super(CODE, cause);
    }
}
