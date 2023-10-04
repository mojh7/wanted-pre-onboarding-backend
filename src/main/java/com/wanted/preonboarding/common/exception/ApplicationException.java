package com.wanted.preonboarding.common.exception;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {
    private ErrorCode errorCode;

    public ApplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
    }
}
