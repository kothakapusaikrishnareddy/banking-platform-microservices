package com.bank.transactionservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawRequest {

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Amount is required")
    @DecimalMin(
            value = "1.00",
            message = "Minimum withdrawal amount is 1"
    )
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    private String currency;

    private String remarks;
}