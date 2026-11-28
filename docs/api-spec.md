# backend-purchase-delivery API 정의서 (구매/배송 서비스)

| 요구사항 이름 | HTTP 메소드 | Version | URI |
|---|---|---|---|
| 구매 내역 목록 조회 | GET | /api/v1 | /orders |
| 판매 내역 목록 조회 | GET | /api/v1 | /orders/sell |
| 거래 상세 조회 | GET | /api/v1 | /orders/{tradeUuid} |
| 구매 확정 | POST | /api/v1 | /orders/{tradeUuid}/confirm |
| 거래 취소 요청 | POST | /api/v1 | /orders/{tradeUuid}/cancel |
| 배송 조회 | GET | /api/v1 | /orders/{tradeUuid}/shipments |

> API 상세 스펙은 기능 구현과 함께 보완한다.
