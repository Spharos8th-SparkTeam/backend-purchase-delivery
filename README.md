# backend-purchase-delivery

ReSpark(글로벌 완구 중고거래 플랫폼)의 **구매/배송 서비스**입니다. MSA 구조에서 거래(trade)와 배송(fulfillment) 도메인을 담당합니다.

## 프로젝트 소개 및 목적

- 구매·판매 내역 조회/상세, 구매 확정, 거래 취소, 배송 조회를 제공한다.
- 다른 서비스와는 각 서비스가 제공하는 API로만 통신한다.

## 주요 기능

기능 명세와 API 정의는 `docs`를 기준으로 한다.

- 구매/판매 내역 조회 및 상세
- 구매 확정
- 거래 취소
- 배송(운송장/스캔 이력) 조회

> 현재는 서비스 기본 환경 설정 단계이며, 위 기능은 순차적으로 구현한다.

## 기술 스택

| 영역 | 기술 |
| :--- | :--- |
| 언어 / 프레임워크 | Java 17, Spring Boot 4.0.10 |
| 빌드 | Gradle |
| 데이터베이스 | MySQL, Spring Data JPA |
| 보안 | Gateway JWT 검증 · 서비스는 비즈니스 권한 검증 |
| API 문서 | springdoc-openapi (Swagger UI) |
| 기타 | Lombok |
| CI/CD | GitHub Actions, Docker |

## MSA 서비스 구성 및 역할

| 서비스 | 역할 |
| :--- | :--- |
| backend-user | 회원 |
| backend-product | 상품 |
| **backend-purchase-delivery** | **구매 & 배송 (현재 서비스)** |
| backend-chat | 채팅 |
| backend-payment | 결제 |
| backend-settlement | 정산 |
| backend-utility | 알림, 게시판 등 |

전체 구성은 [`docs/project-overview.md`](docs/project-overview.md)를 참고한다.

### 인증/인가 역할 분담

| 역할 | 담당 |
| :--- | :--- |
| 회원 인증 및 JWT 발급 | Member Service |
| JWT 검증 및 공통 접근 제어 | Gateway |
| 비즈니스 예외 처리와 세부 권한 검증 | 각 서비스 |

## 디렉터리 구조

```
backend-purchase-delivery
├── .github/workflows/        # CI/CD (GitHub Actions)
├── docs/
│   ├── project-overview.md   # 아키텍처 개요
│   ├── feature-spec.md       # 기능 명세서
│   └── api-spec.md           # API 정의서
└── src/main/java/com/spark/backend_purchase_delivery/
    └── BackendPurchaseDeliveryApplication.java
```

## 실행 방법

### 사전 준비

- JDK 17
- MySQL (로컬 `localhost:3306`, 데이터베이스 `trade`)
- `.env.example`을 참고해 로컬 `.env`를 구성한다

### 환경변수

| 변수 | 설명 | 예시 |
| :--- | :--- | :--- |
| `DB_URL` | JDBC URL | `jdbc:mysql://localhost:3306/trade?serverTimezone=Asia/Seoul&characterEncoding=UTF-8` |
| `DB_USERNAME` | DB 사용자 | `local_user` |
| `DB_PASSWORD` | DB 비밀번호 | (로컬 값) |

로컬 DB는 `trade` 스키마를 기준으로 한다. 인프라 MySQL 계정/스키마 생성은 평일 인프라 설정 때 진행한다.

### 실행

```bash
# Windows
gradlew.bat bootRun

# macOS / Linux
./gradlew bootRun
```

- 서버 포트: `8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

### 빌드 및 테스트

```bash
./gradlew build
```

## 협업 및 Git 브랜치 전략

- 브랜치는 `main`(배포) / `develop` / `feature` 로 구성한다.
- 작업 브랜치 이름은 `feature/{이슈번호}-{작업내용}` 형식이다. 예: `feature/1-init`
- 이슈를 먼저 생성한 뒤 `develop`에서 브랜치를 만들고, 작업 완료 후 `develop`으로 PR을 요청한다.
- 커밋 메시지는 `타입: 요약` 형식이다. 예: `feat: add error handling`
