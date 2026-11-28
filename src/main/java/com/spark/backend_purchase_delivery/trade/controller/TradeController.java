package com.spark.backend_purchase_delivery.trade.controller;

import com.spark.backend_purchase_delivery.common.error.BusinessException;
import com.spark.backend_purchase_delivery.common.error.ErrorCode;
import com.spark.backend_purchase_delivery.trade.dto.request.TradeCancelRequest;
import com.spark.backend_purchase_delivery.trade.dto.response.TradeActionResponse;
import com.spark.backend_purchase_delivery.trade.dto.response.TradeDetailResponse;
import com.spark.backend_purchase_delivery.trade.dto.response.TradeSummaryResponse;
import com.spark.backend_purchase_delivery.trade.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Trade", description = "구매/판매 거래 API")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @Operation(summary = "구매 내역 목록 조회")
    @GetMapping
    public ResponseEntity<List<TradeSummaryResponse>> getPurchaseTrades(
            @RequestHeader("X-Member-UUID") UUID memberId
    ) {
        validateMemberId(memberId);
        return ResponseEntity.ok(tradeService.getPurchaseTrades(memberId));
    }

    @Operation(summary = "판매 내역 목록 조회")
    @GetMapping("/sell")
    public ResponseEntity<List<TradeSummaryResponse>> getSellTrades(
            @RequestHeader("X-Member-UUID") UUID memberId
    ) {
        validateMemberId(memberId);
        return ResponseEntity.ok(tradeService.getSellTrades(memberId));
    }

    @Operation(summary = "거래 상세 조회")
    @GetMapping("/{tradeId}")
    public ResponseEntity<TradeDetailResponse> getTradeDetail(
            @PathVariable UUID tradeId,
            @RequestHeader("X-Member-UUID") UUID memberId
    ) {
        validateMemberId(memberId);
        return ResponseEntity.ok(tradeService.getTradeDetail(tradeId, memberId));
    }

    @Operation(summary = "구매 확정")
    @PostMapping("/{tradeId}/confirm")
    public ResponseEntity<TradeActionResponse> confirmTrade(
            @PathVariable UUID tradeId,
            @RequestHeader("X-Member-UUID") UUID memberId
    ) {
        validateMemberId(memberId);
        return ResponseEntity.ok(tradeService.confirmTrade(tradeId, memberId));
    }

    @Operation(summary = "거래 취소 요청")
    @PostMapping("/{tradeId}/cancel")
    public ResponseEntity<TradeActionResponse> cancelTrade(
            @PathVariable UUID tradeId,
            @RequestHeader("X-Member-UUID") UUID memberId,
            @Valid @RequestBody TradeCancelRequest request
    ) {
        validateMemberId(memberId);
        return ResponseEntity.ok(tradeService.cancelTrade(tradeId, memberId, request));
    }

    private void validateMemberId(UUID memberId) {
        if (memberId == null) {
            throw new BusinessException(ErrorCode.MISSING_MEMBER_UUID);
        }
    }
}