package com.spark.backend_purchase_delivery.shipment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ShipmentCheckpointResponse {

    private String statusCode;
    private String description;
    private LocalDateTime occurredAt;
}