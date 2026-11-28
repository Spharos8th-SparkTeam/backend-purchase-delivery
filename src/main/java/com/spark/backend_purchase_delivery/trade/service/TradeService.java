package com.spark.backend_purchase_delivery.trade.service;

import com.spark.backend_purchase_delivery.common.error.BusinessException;
import com.spark.backend_purchase_delivery.common.error.ErrorCode;
import com.spark.backend_purchase_delivery.trade.dto.request.TradeCancelRequest;
import com.spark.backend_purchase_delivery.trade.dto.response.TradeActionResponse;
import com.spark.backend_purchase_delivery.trade.dto.response.TradeDetailResponse;
import com.spark.backend_purchase_delivery.trade.dto.response.TradeSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class TradeService {

    // TODO: Trade Entity/Repository 연동 후 구현 (#6)
    public List<TradeSummaryResponse> getPurchaseTrades(UUID memberId) {
        return Collections.emptyList();
    }

    // TODO: Trade Entity/Repository 연동 후 구현 (#6)
    public List<TradeSummaryResponse> getSellTrades(UUID memberId) {
        return Collections.emptyList();
    }

    // TODO: Trade Entity/Repository 연동 후 구현 (#6)
    public TradeDetailResponse getTradeDetail(UUID tradeId, UUID memberId) {
        throw new BusinessException(ErrorCode.FEATURE_NOT_READY);
    }

    // TODO: Trade Entity/Repository 연동 후 구현 (#6)
    public TradeActionResponse confirmTrade(UUID tradeId, UUID memberId) {
        throw new BusinessException(ErrorCode.FEATURE_NOT_READY);
    }

    // TODO: Trade Entity/Repository 연동 후 구현 (#6)
    public TradeActionResponse cancelTrade(UUID tradeId, UUID memberId, TradeCancelRequest request) {
        throw new BusinessException(ErrorCode.FEATURE_NOT_READY);
    }
}