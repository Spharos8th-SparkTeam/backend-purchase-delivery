package com.spark.backend_purchase_delivery.trade.enums;

public enum TradePhase {
    REQUESTED,
    AWAITING_PAYMENT,
    PAID,
    SHIPPING,
    DELIVERED,
    CONFIRMED,
    CANCEL_REQUESTED,
    CANCELED
}