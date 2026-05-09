package com.portal.payment.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentInitiateRequest {

    @NotNull(message = "Candidate ID is required")
    private Long candidateUserId;

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotNull(message = "Amount is required for verification")
    @DecimalMin(value = "1.00", message = "Amount must be at least 1.00")
    private BigDecimal expectedAmount;
}
