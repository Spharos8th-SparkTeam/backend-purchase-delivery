package com.spark.backend_purchase_delivery.trade.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TradeCancelRequest {

    @NotBlank
    @Size(max = 40)
    private String cancelReasonCode;

    @Size(max = 500)
    private String cancelDetail;
}