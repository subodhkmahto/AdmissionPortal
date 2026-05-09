package com.portal.payment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentWebhookRequest {

    @NotBlank(message = "Order ID is essential to reconcile payment")
    private String orderId;

    @NotBlank(message = "Gateway Reference is required")
    private String gatewayReferenceId;

    @NotBlank(message = "Status is required")
    private String status; // Usually SUCCESS or FAILED sent by the Gateway

    private String paymentMethod;
}
