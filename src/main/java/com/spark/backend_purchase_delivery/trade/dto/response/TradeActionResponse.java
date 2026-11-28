package com.spark.backend_purchase_delivery.trade.dto.response;

import com.spark.backend_purchase_delivery.trade.enums.TradePhase;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class TradeActionResponse {

    private UUID tradeId;
    private TradePhase phase;
    private String message;
}