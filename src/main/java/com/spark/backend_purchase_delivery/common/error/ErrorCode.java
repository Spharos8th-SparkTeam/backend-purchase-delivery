package com.spark.backend_purchase_delivery.common.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    MISSING_PATH_VARIABLE(400, 4000, "필수 경로 변수가 누락되었습니다"),
    MISSING_MEMBER_UUID(400, 4001, "회원 식별자가 누락되었습니다"),
    INVALID_REQUEST(400, 4002, "잘못된 요청입니다"),
    NOT_AUTHORIZED_OTHER(403, 4300, "권한이 없습니다"),
    TRADE_NOT_FOUND(404, 4400, "거래를 찾을 수 없습니다"),
    SHIPMENT_NOT_FOUND(404, 4401, "배송 정보를 찾을 수 없습니다"),
    FEATURE_NOT_READY(501, 5100, "DB 연동 전 단계입니다. 곧 제공됩니다"),
    INTERNAL_SERVER_ERROR(500, 5000, "서버 내부 오류입니다");

    private final int httpStatus;
    private final int code;
    private final String message;

    ErrorCode(int httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}