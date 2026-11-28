package com.spark.backend_purchase_delivery.common.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //HTTP 400 BAD Request (code:4000~4099)
    MISSING_PATH_VARIABLE(400, 4000, "필수 경로 변수가 누락되었습니다"),
    EXTERNAL_CLIENT_ERROR(400, 4001, "외부 API 요청 오류"),
    EXTERNAL_CLIENT_ERROR_KAKAO(400, 4002, "카카오 API 요청 오류"),
    EXTERNAL_CLIENT_ERROR_GOOGLE(400, 4003, "구글 API 요청 오류"),
    EXTERNAL_CLIENT_ERROR_ODCLOUD(400, 4004, "공공데이터 요청 오류"),

    //HTTP 401 Unauthorized (code:4100~4199)
    INVALID_JWT(401, 4100, "유효하지 않은 JWT 토큰입니다."),
    EXPIRED_JWT(401, 4101, "만료된 JWT 토큰입니다."),

    //HTTP 403 Forbidden (code:4300~4399)
    NOT_AUTHORIZED_OTHER(403, 4300, "권한이 없습니다"),

    //HTTP 500 InternalServer Error (code:5000~5099)
    INTERNAL_SERVER_ERROR(500, 5000, "서버 내부 오류입니다"),

    //HTTP 502 Bad Gateway (code:5200~5299)
    EXTERNAL_SERVER_ERROR(502, 5200, "외부 API 서버 오류"),
    EXTERNAL_SERVER_ERROR_KAKAO(502, 5201, "카카오 API 서버 오류"),
    EXTERNAL_SERVER_ERROR_GOOGLE(502, 5202, "구글 API 서버 오류"),
    EXTERNAL_SERVER_ERROR_ODCLOUD(502, 5203, "공공데이터 서버 오류");

    private final int httpStatus;
    private final int code;
    private final String message;

    ErrorCode(int httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
