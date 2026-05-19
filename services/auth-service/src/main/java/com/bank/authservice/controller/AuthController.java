package com.bank.authservice.controller;

import com.bank.authservice.dto.request.LoginRequest;
import com.bank.authservice.dto.request.RegisterRequest;
import com.bank.authservice.dto.response.LoginResponse;
import com.bank.authservice.dto.response.RegisterResponse;
import com.bank.authservice.exception.InvalidCredentialsException;
import com.bank.authservice.exception.UserException;
import com.bank.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws UserException {

        RegisterResponse successMessage = authService.registerUser(registerRequest);

        return new ResponseEntity<>(successMessage,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) throws  InvalidCredentialsException {

        LoginResponse response = authService.login(loginRequest);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
