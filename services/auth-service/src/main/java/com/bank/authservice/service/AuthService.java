package com.bank.authservice.service;

import com.bank.authservice.dto.request.LoginRequest;
import com.bank.authservice.dto.request.RegisterRequest;
import com.bank.authservice.dto.response.LoginResponse;
import com.bank.authservice.dto.response.RegisterResponse;
import com.bank.authservice.exception.InvalidCredentialsException;
import com.bank.authservice.exception.UserException;

public interface AuthService {

    RegisterResponse registerUser(RegisterRequest registerRequest) throws UserException;
    LoginResponse login(LoginRequest loginRequest) throws  InvalidCredentialsException;

}
