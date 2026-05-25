package com.bank.customerservice.exception;

import com.bank.customerservice.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Customer Profile Already Exists
    @ExceptionHandler(
            CustomerProfileAlreadyExistsException.class
    )
    public ResponseEntity<ApiResponse<Object>>
    handleCustomerProfileAlreadyExistsException(
            CustomerProfileAlreadyExistsException ex
    ) {

        ApiResponse<Object> response =
                ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.CONFLICT
        );
    }

    // Aadhaar Already Exists
    @ExceptionHandler(
            AadhaarAlreadyExistsException.class
    )
    public ResponseEntity<ApiResponse<Object>>
    handleAadhaarAlreadyExistsException(
            AadhaarAlreadyExistsException ex
    ) {

        ApiResponse<Object> response =
                ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.CONFLICT
        );
    }

    // PAN Already Exists
    @ExceptionHandler(
            PanAlreadyExistsException.class
    )
    public ResponseEntity<ApiResponse<Object>>
    handlePanAlreadyExistsException(
            PanAlreadyExistsException ex
    ) {

        ApiResponse<Object> response =
                ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.CONFLICT
        );
    }

    // Customer Not Found
    @ExceptionHandler(
            CustomerNotFoundException.class
    )
    public ResponseEntity<ApiResponse<Object>>
    handleCustomerNotFoundException(
            CustomerNotFoundException ex
    ) {

        ApiResponse<Object> response =
                ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND
        );
    }

    // Validation Errors
    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ApiResponse<Object>>
    handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors =
                new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->

                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ApiResponse<Object> response =
                ApiResponse.builder()
                        .success(false)
                        .message("Validation failed")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .errors(errors)
                        .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }

    // Generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>>
    handleGenericException(
            Exception ex
    ) {

        ApiResponse<Object> response =
                ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .status(
                                HttpStatus.INTERNAL_SERVER_ERROR
                                        .value()
                        )
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}