package com.bank.accountservice.controller;

import com.bank.accountservice.dto.request.CreditRequest;
import com.bank.accountservice.dto.request.DebitRequest;
import com.bank.accountservice.dto.response.ApiResponse;
import com.bank.accountservice.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/internal/accounts")
@RequiredArgsConstructor
public class InternalAccountController {

    private final BankAccountService
            bankAccountService;

    @PostMapping("/debit")
    public ResponseEntity<ApiResponse<String>>
    debitAccount(
            @Valid @RequestBody
            DebitRequest request
    ) {

        bankAccountService.debitAccount(request);

        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .success(true)
                        .message(
                                "Amount debited successfully"
                        )
                        .data("DEBIT_SUCCESS")
                        .status(200)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/credit")
    public ResponseEntity<ApiResponse<String>>
    creditAccount(
            @Valid @RequestBody
            CreditRequest request
    ) {

        bankAccountService.creditAccount(request);

        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .success(true)
                        .message(
                                "Amount credited successfully"
                        )
                        .data("CREDIT_SUCCESS")
                        .status(200)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }
}