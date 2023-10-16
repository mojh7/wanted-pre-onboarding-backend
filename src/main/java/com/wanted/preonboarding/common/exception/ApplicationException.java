package com.wanted.preonboarding.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationException extends RuntimeException {
    private ErrorCode errorCode;
    private String detail;

    public ApplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApplicationException(ErrorCode errorCode, String detail) {
        super(message(errorCode, detail));
        this.errorCode = errorCode;
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
    }

    public ApplicationException(ErrorCode errorCode, Throwable cause, String detail) {
        super(message(errorCode, detail), cause);
    }

    private static String message(ErrorCode errorCode, String detail) {
        if(detail == null) {
            return errorCode.getMessage();
        }

        return "${errorCode.getMessage()} ${detail}";
    }
}
