package com.bank.customerservice.dto.request;

import com.bank.customerservice.entity.Gender;
import com.bank.customerservice.entity.MaritalStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCustomerProfileRequest {



    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private LocalDate dateOfBirth;

    private Gender gender;

    @Pattern(
            regexp = "^[0-9]{12}$",
            message = "Aadhaar number must be 12 digits"
    )
    private String aadhaarNumber;

    @Pattern(
            regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$",
            message = "Invalid PAN format"
    )
    private String panNumber;

    private String occupation;

    private BigDecimal annualIncome;

    private MaritalStatus maritalStatus;

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must be 10 digits"
    )
    private String phoneNumber;

    @Valid
    private List<AddressRequest> addresses;
}