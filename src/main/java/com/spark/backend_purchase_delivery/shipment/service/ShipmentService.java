package com.spark.backend_purchase_delivery.shipment.service;

import com.spark.backend_purchase_delivery.common.error.BusinessException;
import com.spark.backend_purchase_delivery.common.error.ErrorCode;
import com.spark.backend_purchase_delivery.shipment.dto.response.ShipmentResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShipmentService {

    // TODO: Shipment Entity/Repository 연동 후 구현 (#6)
    public ShipmentResponse getShipmentByTradeId(UUID tradeId, UUID memberId) {
        throw new BusinessException(ErrorCode.FEATURE_NOT_READY);
    }
}