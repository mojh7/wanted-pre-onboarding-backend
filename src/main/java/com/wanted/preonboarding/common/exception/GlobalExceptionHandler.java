package com.wanted.preonboarding.common.exception;

import com.wanted.preonboarding.common.ApiResponse;
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
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
          .getAllErrors()
          .forEach(error -> errors.put(((FieldError) error).getField(),
                  error.getDefaultMessage()));

        log.warn("MethodArgumentNotValid ", ex);
        return ApiResponse.error(errors);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleHttpMessageConversionException(HttpMessageConversionException ex) {
        log.warn("HttpMessageConversion ", ex);
        return ApiResponse.error("Bad request body");
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(ApplicationException ex) {
        log.warn("CustomException ", ex);
        return ResponseEntity.status(ex.getErrorCode().getStatus())
                             .body(ApiResponse.error(ex));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<?> handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("AccessDenied ", ex);
        return ApiResponse.error("Access denied");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleException(Exception ex) {
        log.error("Internal server error", ex);
        return ApiResponse.error("Internal server error");
    }
}
