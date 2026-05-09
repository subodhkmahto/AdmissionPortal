package com.portal.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendNotificationRequest {

    @NotNull
    private Long candidateUserId;

    @NotBlank
    private String type; // SMS or EMAIL

    @NotBlank
    private String recipient;

    @NotBlank
    private String subject;

    @NotBlank
    private String messageBody;
}
