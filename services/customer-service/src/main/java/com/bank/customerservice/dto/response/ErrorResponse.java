package com.bank.customerservice.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private boolean success;

    private String message;

    private int status;

    private LocalDateTime timestamp;

    // For validation errors
    private Map<String, String> errors;
}