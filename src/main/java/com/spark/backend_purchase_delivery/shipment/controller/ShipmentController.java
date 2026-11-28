package com.spark.backend_purchase_delivery.shipment.controller;

import com.spark.backend_purchase_delivery.common.error.BusinessException;
import com.spark.backend_purchase_delivery.common.error.ErrorCode;
import com.spark.backend_purchase_delivery.shipment.dto.response.ShipmentResponse;
import com.spark.backend_purchase_delivery.shipment.service.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Shipment", description = "배송 조회 API")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @Operation(summary = "배송 조회")
    @GetMapping("/{tradeId}/shipments")
    public ResponseEntity<ShipmentResponse> getShipment(
            @PathVariable UUID tradeId,
            @RequestHeader("X-Member-UUID") UUID memberId
    ) {
        if (memberId == null) {
            throw new BusinessException(ErrorCode.MISSING_MEMBER_UUID);
        }
        return ResponseEntity.ok(shipmentService.getShipmentByTradeId(tradeId, memberId));
    }
}