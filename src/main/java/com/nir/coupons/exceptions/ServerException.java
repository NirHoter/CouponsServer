package com.nir.coupons.exceptions;

import com.nir.coupons.enums.ErrorType;

public class ServerException extends Exception {
    private ErrorType errorType;

    public ServerException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ServerException(ErrorType errorType) {
        this(errorType, "");
    }

    public ServerException(ErrorType errorType, Exception e) {
        super(e);
        this.errorType = errorType;
    }

    public ServerException(ErrorType errorType, Exception e, String message) {
        super(message, e);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
