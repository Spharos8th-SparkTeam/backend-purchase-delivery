package com.spark.backend_purchase_delivery.common.error;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException ex) {
        ErrorCode ec = ex.getErrorCode();
        ErrorResponseDto body = new ErrorResponseDto(
                LocalDateTime.now(),
                ec.getHttpStatus(),
                ec.getCode(),
                ec.getMessage()
        );
        return ResponseEntity.status(ec.getHttpStatus()).body(body);
    }

    @ExceptionHandler(CustomSecurityException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomSecurityException(CustomSecurityException ex) {
        return buildErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpClientError(HttpClientErrorException ex) {
        ErrorCode errorCode = resolveClientErrorCode(ex);
        return buildErrorResponse(errorCode);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpServerError(HttpServerErrorException ex) {
        ErrorCode errorCode = resolveServerErrorCode(ex);
        return buildErrorResponse(errorCode);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponseDto> handleJwtException(JwtException e) {
        ErrorCode errorCode;

        if (e instanceof ExpiredJwtException) {
            errorCode = ErrorCode.EXPIRED_JWT;
        } else if (e instanceof MalformedJwtException) {
            errorCode = ErrorCode.INVALID_JWT;
        } else if (e instanceof SignatureException) {
            errorCode = ErrorCode.INVALID_JWT;
        } else if (e instanceof JwtException) {
            errorCode = ErrorCode.INVALID_JWT;
        } else {
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        }
        log.error(errorCode.getMessage());

        return buildErrorResponse(errorCode);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingPathVariable(MissingPathVariableException ex) {
        ErrorCode errorCode = ErrorCode.MISSING_PATH_VARIABLE;
        log.warn("누락된 경로 변수: {}", ex.getVariableName());
        return buildErrorResponse(errorCode);
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

    private ErrorCode resolveClientErrorCode(HttpClientErrorException ex) {
        String msg = ex.getMessage().toLowerCase();
        if (msg.contains("kakao")) {
            return ErrorCode.EXTERNAL_CLIENT_ERROR_KAKAO;
        }
        if (msg.contains("google")) {
            return ErrorCode.EXTERNAL_CLIENT_ERROR_GOOGLE;
        }
        if (msg.contains("odcloud") || msg.contains("nts-businessman")) {
            return ErrorCode.EXTERNAL_CLIENT_ERROR_ODCLOUD;
        }
        return ErrorCode.EXTERNAL_CLIENT_ERROR;
    }

    private ErrorCode resolveServerErrorCode(HttpServerErrorException ex) {
        String msg = ex.getMessage().toLowerCase();
        if (msg.contains("kakao")) {
            return ErrorCode.EXTERNAL_SERVER_ERROR_KAKAO;
        }
        if (msg.contains("google")) {
            return ErrorCode.EXTERNAL_SERVER_ERROR_GOOGLE;
        }
        if (msg.contains("odcloud") || msg.contains("nts-businessman")) {
            return ErrorCode.EXTERNAL_SERVER_ERROR_ODCLOUD;
        }
        return ErrorCode.EXTERNAL_SERVER_ERROR;
    }
}
