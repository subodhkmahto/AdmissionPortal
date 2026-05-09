package com.portal.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileRequest {

    @NotNull(message = "User ID must be provided")
    private Long userId;

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 100, message = "First name length must be between 2 and 100")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
    private String mobile;

    @NotNull(message = "Date of Birth is mandatory")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is mandatory")
    private String gender;

    private String category;

    @Pattern(regexp = "^\\d{12}$", message = "Aadhar number must be 12 digits if provided")
    private String aadharNumber;
}
