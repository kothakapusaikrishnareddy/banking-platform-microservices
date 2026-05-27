package com.bank.transactionservice.dto.response;

import com.bank.transactionservice.entity.TransactionStatus;
import com.bank.transactionservice.entity.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private String transactionReference;

    private String fromAccountNumber;

    private String toAccountNumber;

    private BigDecimal amount;

    private String currency;

    private TransactionType transactionType;

    private TransactionStatus transactionStatus;

    private String remarks;

    private String initiatedBy;

    private LocalDateTime createdAt;
}