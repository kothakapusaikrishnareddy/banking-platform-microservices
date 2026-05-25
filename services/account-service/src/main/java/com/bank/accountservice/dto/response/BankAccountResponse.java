package com.bank.accountservice.dto.response;

import com.bank.accountservice.entity.AccountStatus;
import com.bank.accountservice.entity.AccountType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccountResponse {

    private String accountNumber;

    private String customerEmail;

    private AccountType accountType;

    private BigDecimal balance;

    private String currency;

    private AccountStatus accountStatus;

    private String branchCode;

    private LocalDateTime createdAt;
}