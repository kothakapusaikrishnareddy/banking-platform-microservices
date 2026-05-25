package com.bank.customerservice.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private boolean success;

    private String message;

    private T data;

    private int status;

    private LocalDateTime timestamp;

    // Optional validation errors
    private Map<String, String> errors;
}