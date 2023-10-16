package com.wanted.preonboarding.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // common 1000 ~
    UNABLE_TO_UPDATE_FIELDS(HttpStatus.BAD_REQUEST, "1000", "수정할 수 없는 필드가 있습니다"),

    // member 2000 ~

    // company 3000 ~
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "3000", "해당 회사를 찾을 수 없습니다"),

    // job post 4000 ~
    JOBPOST_NOT_FOUND(HttpStatus.NOT_FOUND, "4000", "해당 채용 공고를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String code; // 클라이언트 구분용 code
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
