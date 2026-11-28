# Respark 아키텍처 개요

> 글로벌 완구 중고거래 플랫폼 · MSA 구조

## backend-purchase-delivery 책임

- 구매·판매 내역 조회/상세
- 구매 확정, 거래 취소
- 배송 조회

## 인증/인가 역할 분담

| 역할 | 담당 |
| :--- | :--- |
| 회원 인증 및 JWT 발급 | Member Service |
| JWT 검증 및 공통 접근 제어 | Gateway |
| 비즈니스 예외 처리와 세부 권한 검증 | 각 서비스 |

## 기술 스택

| 영역 | 기술 |
| :--- | :--- |
| 언어 / 프레임워크 | Java 17, Spring Boot 4.0.10 |
| 빌드 | Gradle |
| 데이터베이스 | MySQL, Spring Data JPA |
| API 문서 | springdoc-openapi (Swagger UI) |
| CI/CD | GitHub Actions, Docker |
