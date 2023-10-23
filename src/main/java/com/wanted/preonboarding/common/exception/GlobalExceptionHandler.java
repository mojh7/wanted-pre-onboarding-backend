package com.wanted.preonboarding.common.exception;

import com.wanted.preonboarding.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
          .getAllErrors()
          .forEach(error -> errors.put(((FieldError) error).getField(),
                  error.getDefaultMessage()));

        log.warn("MethodArgumentNotValid ", ex);
        return ApiResult.error(errors);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> handleHttpMessageConversionException(HttpMessageConversionException ex) {
        log.warn("HttpMessageConversion ", ex);
        return ApiResult.error("Bad request body");
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResult<?>> handleCustomException(ApplicationException ex) {
        log.warn("CustomException ", ex);
        return ResponseEntity.status(ex.getErrorCode().getStatus())
                             .body(ApiResult.error(ex));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResult<?> handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("AccessDenied ", ex);
        return ApiResult.error("Access denied");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<?> handleException(Exception ex) {
        log.error("Internal server error", ex);
        return ApiResult.error("Internal server error");
    }
}
