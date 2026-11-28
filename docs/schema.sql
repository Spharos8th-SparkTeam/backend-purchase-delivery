-- Schema trade
-- 거래 쓰기 모델. 상품, 회원, 결제, 배송은 UUID 와 구매 시점 스냅샷이다.
-- ---------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS trade
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;
USE trade;

CREATE TABLE cancel_reason (
  id BIGINT NOT NULL AUTO_INCREMENT,
  reason_code VARCHAR(40) NOT NULL,
  label VARCHAR(100) NOT NULL,
  sort_order INT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_cancel_reason (reason_code)
) COMMENT='거래 취소 사유 기준정보';

CREATE TABLE trade (
  id BIGINT NOT NULL AUTO_INCREMENT,
  trade_uuid CHAR(36) NOT NULL,
  product_uuid CHAR(36) NOT NULL,
  seller_uuid CHAR(36) NOT NULL,
  buyer_uuid CHAR(36) NOT NULL,
  phase VARCHAR(32) NOT NULL,
  payment_status VARCHAR(32) NOT NULL,
  seller_nickname VARCHAR(30) NOT NULL,
  buyer_nickname VARCHAR(30) NOT NULL,
  seller_email VARCHAR(254) NOT NULL,
  buyer_email VARCHAR(254) NOT NULL,
  product_title VARCHAR(100) NOT NULL,
  product_thumbnail_key VARCHAR(255) NOT NULL,
  product_price_amount DECIMAL(12,2) NOT NULL,
  delivery_fee_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  currency_code CHAR(3) NOT NULL DEFAULT 'USD',
  membership_applied TINYINT(1) NOT NULL DEFAULT 0,
  fee_rate_percent DECIMAL(5,2) NULL,
  buyer_recipient_name VARCHAR(100) NULL,
  buyer_phone VARCHAR(30) NULL,
  buyer_address_line VARCHAR(255) NULL,
  buyer_address_detail VARCHAR(150) NULL,
  buyer_postcode VARCHAR(20) NULL,
  customs_clearance_code VARCHAR(30) NULL,
  seller_recipient_name VARCHAR(100) NULL,
  seller_phone VARCHAR(30) NULL,
  seller_address_line VARCHAR(255) NULL,
  seller_address_detail VARCHAR(150) NULL,
  seller_postcode VARCHAR(20) NULL,
  payout_holder_name VARCHAR(100) NULL,
  payout_bank_name VARCHAR(100) NULL,
  payout_bank_code VARCHAR(20) NULL,
  payout_account_cipher VARCHAR(512) NULL,
  payout_encryption_key_id VARCHAR(64) NULL,
  cancel_reason_code VARCHAR(40) NULL,
  cancel_detail VARCHAR(500) NULL,
  canceled_by_uuid CHAR(36) NULL,
  accept_due_at DATETIME(6) NULL,
  accepted_at DATETIME(6) NULL,
  paid_at DATETIME(6) NULL,
  delivered_at DATETIME(6) NULL,
  confirm_due_at DATETIME(6) NULL,
  confirmed_at DATETIME(6) NULL,
  cancel_requested_at DATETIME(6) NULL,
  canceled_at DATETIME(6) NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (id),
  UNIQUE KEY uk_trade_uuid (trade_uuid),
  KEY ix_trade_buyer (buyer_uuid, phase, created_at),
  KEY ix_trade_seller (seller_uuid, phase, created_at),
  KEY ix_trade_product (product_uuid),
  KEY ix_trade_accept_due (phase, accept_due_at),
  KEY ix_trade_confirm_due (phase, confirm_due_at),
  CONSTRAINT ck_trade_phase CHECK (phase IN (
    'REQUESTED', 'AWAITING_PAYMENT', 'PAID', 'SHIPPING', 'DELIVERED',
    'CONFIRMED', 'CANCEL_REQUESTED', 'CANCELED'
  )),
  CONSTRAINT ck_trade_payment_status CHECK (payment_status IN (
    'PENDING', 'PAID', 'CANCELED', 'REFUND_PENDING', 'REFUNDED'
  )),
  CONSTRAINT ck_trade_price CHECK (product_price_amount >= 0 AND product_price_amount <= 1000000),
  CONSTRAINT ck_trade_currency CHECK (currency_code = 'USD')
) COMMENT='거래 원장';

CREATE TABLE trade_status_log (
  id BIGINT NOT NULL AUTO_INCREMENT,
  trade_id BIGINT NOT NULL,
  from_phase VARCHAR(32) NULL,
  to_phase VARCHAR(32) NOT NULL,
  actor_uuid CHAR(36) NULL,
  reason_text VARCHAR(200) NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (id),
  KEY ix_trade_status_log (trade_id, created_at),
  CONSTRAINT fk_trade_status_log
    FOREIGN KEY (trade_id) REFERENCES trade (id)
) COMMENT='거래 단계 이력';

CREATE TABLE outbox_event (
  id BIGINT NOT NULL AUTO_INCREMENT,
  event_id CHAR(36) NOT NULL,
  aggregate_type VARCHAR(40) NOT NULL,
  aggregate_uuid CHAR(36) NOT NULL,
  event_type VARCHAR(80) NOT NULL,
  payload JSON NOT NULL,
  occurred_at DATETIME(6) NOT NULL,
  published_at DATETIME(6) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_outbox_event (event_id),
  KEY ix_outbox_unpublished (published_at, id)
) COMMENT='거래 단계 변경 아웃박스';

CREATE TABLE processed_event (
  event_id CHAR(36) NOT NULL,
  event_type VARCHAR(80) NOT NULL,
  processed_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (event_id)
) COMMENT='결제, 배송, 정산 이벤트 멱등 키';

INSERT INTO cancel_reason (reason_code, label, sort_order) VALUES
  ('CHANGED_MIND', '단순 변심', 10),
  ('NOT_AS_DESCRIBED', '상품 설명과 다름', 20),
  ('DAMAGED', '파손 또는 하자', 30),
  ('SELLER_UNRESPONSIVE', '판매자 미응답', 40),
  ('OTHER', '기타', 50);

-- ---------------------------------------------------------------
-- Schema fulfillment
-- 운송장과 택배 스캔 이력.
-- ---------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS fulfillment
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;
USE fulfillment;

CREATE TABLE shipment (
  id BIGINT NOT NULL AUTO_INCREMENT,
  shipment_uuid CHAR(36) NOT NULL,
  trade_uuid CHAR(36) NOT NULL,
  courier_name VARCHAR(50) NOT NULL,
  tracking_number VARCHAR(64) NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'READY',
  shipped_at DATETIME(6) NULL,
  delivered_at DATETIME(6) NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (id),
  UNIQUE KEY uk_shipment_uuid (shipment_uuid),
  UNIQUE KEY uk_shipment_trade (trade_uuid),
  KEY ix_shipment_tracking (courier_name, tracking_number),
  CONSTRAINT ck_shipment_status CHECK (status IN ('READY', 'SHIPPED', 'DELIVERED'))
) COMMENT='거래당 배송 하나';

CREATE TABLE shipment_checkpoint (
  id BIGINT NOT NULL AUTO_INCREMENT,
  shipment_id BIGINT NOT NULL,
  status_code VARCHAR(40) NOT NULL,
  description VARCHAR(255) NOT NULL,
  occurred_at DATETIME(6) NOT NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (id),
  KEY ix_checkpoint_shipment (shipment_id, occurred_at),
  CONSTRAINT fk_checkpoint_shipment
    FOREIGN KEY (shipment_id) REFERENCES shipment (id)
) COMMENT='택배사 조회 스캔';

CREATE TABLE outbox_event (
  id BIGINT NOT NULL AUTO_INCREMENT,
  event_id CHAR(36) NOT NULL,
  aggregate_type VARCHAR(40) NOT NULL,
  aggregate_uuid CHAR(36) NOT NULL,
  event_type VARCHAR(80) NOT NULL,
  payload JSON NOT NULL,
  occurred_at DATETIME(6) NOT NULL,
  published_at DATETIME(6) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_outbox_event (event_id),
  KEY ix_outbox_unpublished (published_at, id)
) COMMENT='출고, 배송 완료 아웃박스';

CREATE TABLE processed_event (
  event_id CHAR(36) NOT NULL,
  event_type VARCHAR(80) NOT NULL,
  processed_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (event_id)
) COMMENT='결제 완료 뒤 배송 행을 만드는 이벤트 멱등 키';
