package com.bank.accountservice.exception;


import com.bank.accountservice.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(
            AccountNotFoundException.class
    )
    public ResponseEntity<ApiResponse<Object>>
    handleAccountNotFoundException(AccountNotFoundException ex
    ) {

        ApiResponse<Object> response =
                ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .status(403)
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateAccountException(DuplicateAccountException ex){
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .status(403)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(
                response,
                HttpStatus.FORBIDDEN
        );
    }
    @ExceptionHandler(
            UnauthorizedAccountAccessException.class
    )
    public ResponseEntity<ApiResponse<Object>>
    handleUnauthorizedAccess(
            UnauthorizedAccountAccessException ex
    ) {

        ApiResponse<Object> response =
                ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .status(403)
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.FORBIDDEN
        );
    }


}
