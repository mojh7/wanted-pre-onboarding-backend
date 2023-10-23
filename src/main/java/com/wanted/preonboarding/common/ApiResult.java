package com.wanted.preonboarding.common;

import com.wanted.preonboarding.common.exception.ApplicationException;
import lombok.Getter;

@Getter
public class ApiResult<T> {
    private final boolean success;
    private final T response;
    private final ErrorResponse<?> error;

    private static final ApiResult<?> SUCCEED_RESPONSE = new ApiResult<>(true, null, null);

    private ApiResult(boolean success, T response, ErrorResponse<?> error) {
        this.success = success;
        this.response = response;
        this.error = error;
    }

    public static ApiResult<?> succeed() {
        return SUCCEED_RESPONSE;
    }

    public static <T> ApiResult<T> succeed(T data) {
        return new ApiResult<>(true, data, null);
    }

    public static ApiResult<?> error(ApplicationException ex) {
        return new ApiResult<>(false, null, new ErrorResponse<>(ex.getErrorCode().getCode(), ex.getErrorMessage()));
    }

    public static <E> ApiResult<?> error(E message) {
        return new ApiResult<>(false, null, new ErrorResponse<>(null, message));
    }

    private static class ErrorResponse<E> {
        private final String code;
        private final E message;

        private ErrorResponse(String code, E message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public E getMessage() {
            return message;
        }
    }
}
