package com.spark.backend_purchase_delivery.trade.dto.response;

import com.spark.backend_purchase_delivery.trade.enums.TradePaymentStatus;
import com.spark.backend_purchase_delivery.trade.enums.TradePhase;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TradeDetailResponse {

    private UUID tradeId;
    private UUID productId;
    private UUID sellerId;
    private UUID buyerId;
    private TradePhase phase;
    private TradePaymentStatus paymentStatus;
    private String sellerNickname;
    private String buyerNickname;
    private String productTitle;
    private String productThumbnailKey;
    private BigDecimal productPriceAmount;
    private BigDecimal deliveryFeeAmount;
    private String currencyCode;
    private String buyerRecipientName;
    private String buyerPhone;
    private String buyerAddressLine;
    private String buyerAddressDetail;
    private String buyerPostcode;
    private LocalDateTime paidAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}