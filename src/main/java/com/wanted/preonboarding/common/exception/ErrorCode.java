package com.wanted.preonboarding.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "300", "해당 회사를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String code; // 클라이언트 구분용 code
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
