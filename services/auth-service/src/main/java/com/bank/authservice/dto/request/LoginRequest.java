package com.bank.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    @Email(message = "Please enter a valid Email")
    @NotBlank(message = "Please enter the email")
    private String email;
    @NotBlank(message = "Please enter the password")
    private String password;
}
