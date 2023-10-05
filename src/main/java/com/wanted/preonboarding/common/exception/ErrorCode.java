package com.wanted.preonboarding.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // TODO: ErrorCode 클래스 생성을 위한 임시 필드, 추후 변경해야됨
    TEMP(HttpStatus.INTERNAL_SERVER_ERROR, "100", "에러 메시지 설명");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
