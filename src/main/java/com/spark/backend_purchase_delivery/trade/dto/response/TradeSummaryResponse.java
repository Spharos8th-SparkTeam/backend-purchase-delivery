package com.spark.backend_purchase_delivery.trade.dto.response;

import com.spark.backend_purchase_delivery.trade.enums.TradePhase;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TradeSummaryResponse {

    private UUID tradeId;
    private UUID productId;
    private String productTitle;
    private String productThumbnailKey;
    private BigDecimal productPriceAmount;
    private TradePhase phase;
    private LocalDateTime createdAt;
}