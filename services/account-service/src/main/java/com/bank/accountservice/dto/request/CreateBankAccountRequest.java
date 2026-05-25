package com.bank.accountservice.dto.request;

import com.bank.accountservice.entity.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBankAccountRequest {

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotBlank(message = "Branch code is required")
    private String branchCode;
}