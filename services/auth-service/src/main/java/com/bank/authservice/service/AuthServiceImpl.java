package com.bank.authservice.service;

import com.bank.authservice.config.SecurityConfig;
import com.bank.authservice.dto.request.LoginRequest;
import com.bank.authservice.dto.request.RegisterRequest;
import com.bank.authservice.dto.response.LoginResponse;
import com.bank.authservice.dto.response.RegisterResponse;
import com.bank.authservice.entity.AccountStatus;
import com.bank.authservice.entity.Role;
import com.bank.authservice.entity.User;
import com.bank.authservice.exception.InvalidCredentialsException;
import com.bank.authservice.exception.UserException;
import com.bank.authservice.repository.UserRepository;
import com.bank.authservice.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) throws UserException {

        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserException("Email already exists");
        }

        if(userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            throw new UserException("Phone number already exists");
        }

        User user = User.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.CUSTOMER)
                .accountStatus(AccountStatus.ACTIVE)
                .emailVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return RegisterResponse.builder()
                .message("User registered successfully")
                .build();

    }

    public LoginResponse login(LoginRequest loginRequest) throws  InvalidCredentialsException {

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new InvalidCredentialsException("Invalid email or password"));

        boolean isPasswordMatched = passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()
        );

        if (!isPasswordMatched) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String jwtToken = jwtService.generateToken(user.getEmail(),user.getRole());

        // Return response
        return LoginResponse.builder()
                .accessToken(jwtToken)
                .build();

    }
}
