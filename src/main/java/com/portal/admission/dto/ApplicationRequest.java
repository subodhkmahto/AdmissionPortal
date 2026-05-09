package com.portal.admission.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRequest {

    @NotNull(message = "Candidate User ID is required")
    private Long candidateUserId;

    @NotNull(message = "Course ID is required")
    private Long courseId;
}
