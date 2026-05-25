package com.bank.accountservice.controller;


import com.bank.accountservice.dto.request.CreateBankAccountRequest;
import com.bank.accountservice.dto.response.ApiResponse;
import com.bank.accountservice.dto.response.BalanceResponse;
import com.bank.accountservice.dto.response.BankAccountResponse;
import com.bank.accountservice.service.BankAccountService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;


    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BankAccountResponse>> createAccount(@Valid @RequestBody CreateBankAccountRequest createBankAccountRequest, Authentication authentication){

        String email = authentication.getName();
        BankAccountResponse bankAccountResponse = bankAccountService.createBankAccount(createBankAccountRequest, email);

        ApiResponse<BankAccountResponse> apiResponse = ApiResponse.<BankAccountResponse>builder()
                .success(true)
                .message("Bank account created successfully")
                .data(bankAccountResponse)
                .status(201)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<BankAccountResponse>>> getMyAccounts(Authentication authentication){

        String email = authentication.getName();

        List<BankAccountResponse> bankAccounts = bankAccountService.getMyAccounts(email);
        ApiResponse<List<BankAccountResponse>> response = ApiResponse.<List<BankAccountResponse>>builder()
                .success(true)
                .message("All bank account details fetched successfully")
                .data(bankAccounts)
                .status(200)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/details/{accountNumber}")
    public ResponseEntity<
            ApiResponse<BankAccountResponse>
            > getAccountDetails(

            @PathVariable String accountNumber,
            Authentication authentication
    ) {

        String customerEmail =
                authentication.getName();

        BankAccountResponse response =
                bankAccountService.getAccountDetails(
                        accountNumber,
                        customerEmail
                );

        ApiResponse<BankAccountResponse>
                apiResponse =

                ApiResponse
                        .<BankAccountResponse>builder()
                        .success(true)
                        .message(
                                "Account details fetched successfully"
                        )
                        .data(response)
                        .status(200)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<
            ApiResponse<BalanceResponse>
            > getBalance(

            @PathVariable String accountNumber,
            Authentication authentication
    ) {

        String customerEmail =
                authentication.getName();

        BalanceResponse response =
                bankAccountService.getBalance(
                        accountNumber,
                        customerEmail
                );

        ApiResponse<BalanceResponse>
                apiResponse =

                ApiResponse
                        .<BalanceResponse>builder()
                        .success(true)
                        .message(
                                "Balance fetched successfully"
                        )
                        .data(response)
                        .status(200)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(apiResponse);
    }
}
