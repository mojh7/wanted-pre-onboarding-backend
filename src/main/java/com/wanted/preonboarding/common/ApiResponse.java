package com.wanted.preonboarding.common;

import com.wanted.preonboarding.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final boolean success;
    private final T response;
    private final ErrorResponse<?> error;

    private static final ApiResponse<?> SUCCEED_RESPONSE = new ApiResponse<>(true, null, null);

    private ApiResponse(boolean success, T response, ErrorResponse<?> error) {
        this.success = success;
        this.response = response;
        this.error = error;
    }

    public static ApiResponse<?> succeed() {
        return SUCCEED_RESPONSE;
    }

    public static <T> ApiResponse<T> succeed(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static ApiResponse<?> error(ErrorCode errorCode) {
        return new ApiResponse<>(false, null, new ErrorResponse<>(errorCode.getCode(), errorCode.getMessage()));
    }

    public static <E> ApiResponse<?> error(E message) {
        return new ApiResponse<>(false, null, new ErrorResponse<>(null, message));
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
