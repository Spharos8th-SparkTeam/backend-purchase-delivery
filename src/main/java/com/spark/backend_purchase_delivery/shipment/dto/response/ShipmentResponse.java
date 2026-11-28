package com.spark.backend_purchase_delivery.shipment.dto.response;

import com.spark.backend_purchase_delivery.shipment.enums.ShipmentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class ShipmentResponse {

    private UUID shipmentId;
    private UUID tradeId;
    private String courierName;
    private String trackingNumber;
    private ShipmentStatus status;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private List<ShipmentCheckpointResponse> checkpoints;
}