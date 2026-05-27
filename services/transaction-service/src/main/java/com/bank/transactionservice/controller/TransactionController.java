package com.bank.transactionservice.controller;


import com.bank.transactionservice.dto.request.TransferRequest;
import com.bank.transactionservice.dto.response.ApiResponse;
import com.bank.transactionservice.dto.response.TransactionResponse;
import com.bank.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransactionResponse>> transferMoney(@Valid @RequestBody TransferRequest request, Authentication authentication) {

        String initiatedBy = authentication.getName();

        TransactionResponse response = transactionService.transferMoney(request, initiatedBy);

        ApiResponse<TransactionResponse> apiResponse = ApiResponse.<TransactionResponse>builder()
                .success(true)
                .message("Transfer successful")
                .data(response)
                .status(201)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity(apiResponse, HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('CUSTOMER")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getMyTransactions(Authentication authentication) {

        String initiatedBy = authentication.getName();

        List<TransactionResponse> transactions = transactionService.getMyTransactions(initiatedBy);

        ApiResponse<List<TransactionResponse>> apiResponse = ApiResponse.<List<TransactionResponse>>builder()
                .success(true)
                .message("All transactions fetched successfully")
                .data(transactions)
                .status(200)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}


