package com.spark.backend_purchase_delivery.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException ex) {
        return buildErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingPathVariable(MissingPathVariableException ex) {
        log.warn("누락된 경로 변수: {}", ex.getVariableName());
        return buildErrorResponse(ErrorCode.MISSING_PATH_VARIABLE);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingRequestHeader(MissingRequestHeaderException ex) {
        log.warn("누락된 요청 헤더: {}", ex.getHeaderName());
        return buildErrorResponse(ErrorCode.MISSING_MEMBER_UUID);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return buildErrorResponse(ErrorCode.INVALID_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        log.error("서버 내부 오류", ex);
        return buildErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(ErrorCode ec) {
        ErrorResponseDto body = new ErrorResponseDto(
                LocalDateTime.now(),
                ec.getHttpStatus(),
                ec.getCode(),
                ec.getMessage()
        );
        return ResponseEntity.status(ec.getHttpStatus()).body(body);
    }
}