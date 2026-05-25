package com.bank.customerservice.controller;

import com.bank.customerservice.dto.request.CreateCustomerProfileRequest;
import com.bank.customerservice.dto.response.ApiResponse;
import com.bank.customerservice.dto.response.CustomerProfileResponse;
import com.bank.customerservice.exception.AadhaarAlreadyExistsException;
import com.bank.customerservice.exception.CustomerNotFoundException;
import com.bank.customerservice.exception.CustomerProfileAlreadyExistsException;
import com.bank.customerservice.exception.PanAlreadyExistsException;
import com.bank.customerservice.service.CustomerProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerProfileController {

    @Autowired
    private CustomerProfileService customerProfileService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CustomerProfileResponse>> createCustomerProfile(@Valid @RequestBody CreateCustomerProfileRequest createCustomerProfileRequest, Authentication authentication) throws CustomerProfileAlreadyExistsException, AadhaarAlreadyExistsException, PanAlreadyExistsException {

        String email = authentication.getName();

        CustomerProfileResponse customerProfileResponse = customerProfileService.createCustomerProfile(createCustomerProfileRequest, email);

        ApiResponse<CustomerProfileResponse> response = ApiResponse.<CustomerProfileResponse>builder()
                .success(true)
                .message(
                        "Customer profile created successfully"
                )
                .data(customerProfileResponse)
                .status(201)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<CustomerProfileResponse>> getCustomerProfile(Authentication authentication) throws CustomerNotFoundException {

        String email = authentication.getName();
        CustomerProfileResponse customerProfileResponse = customerProfileService.getCustomerProfile(email);

        ApiResponse<CustomerProfileResponse> response = ApiResponse.<CustomerProfileResponse>builder()
                .success(true)
                .message(
                        "Customer profile fetched successfully"
                )
                .data(customerProfileResponse)
                .status(201)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<
            ApiResponse<List<CustomerProfileResponse>>
            > getAllCustomers() {

        List<CustomerProfileResponse> customers =
                customerProfileService
                        .getAllCustomers();

        ApiResponse<List<CustomerProfileResponse>>
                apiResponse =
                ApiResponse
                        .<List<CustomerProfileResponse>>builder()
                        .success(true)
                        .message(
                                "Customers fetched successfully"
                        )
                        .data(customers)
                        .status(200)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(apiResponse);
    }
}
