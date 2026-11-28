# backend-purchase-delivery API 정의서 (구매/배송 서비스)

> 현재는 테이블 연동 전 API 골격 단계다. Service는 stub이며 DB 확정 후 구현한다.
> 회원 식별은 Gateway 연동 전 `X-Member-UUID` 헤더로 받는다.

| 요구사항 이름 | HTTP 메소드 | Version | URI | 비고 |
|---|---|---|---|---|
| 구매 내역 목록 조회 | GET | /api/v1 | /orders | Header: X-Member-UUID |
| 판매 내역 목록 조회 | GET | /api/v1 | /orders/sell | Header: X-Member-UUID |
| 거래 상세 조회 | GET | /api/v1 | /orders/{tradeId} | tradeId = trade UUID |
| 구매 확정 | POST | /api/v1 | /orders/{tradeId}/confirm | |
| 거래 취소 요청 | POST | /api/v1 | /orders/{tradeId}/cancel | body: cancelReasonCode, cancelDetail |
| 배송 조회 | GET | /api/v1 | /orders/{tradeId}/shipments | |

## stub 동작

- 목록 조회: 빈 배열
- 상세/확정/취소/배송: `FEATURE_NOT_READY(501, 5100)`
