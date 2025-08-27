package com.mlbyl.bankingproject.controller;

import com.mlbyl.bankingproject.dto.Auth.request.LoginRequest;
import com.mlbyl.bankingproject.dto.Auth.response.AuthResponse;
import com.mlbyl.bankingproject.security.jwt.JwtService;
import com.mlbyl.bankingproject.service.abstracts.AuthService;
import com.mlbyl.bankingproject.utilities.constants.SuccessMessages;
import com.mlbyl.bankingproject.utilities.results.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<Result<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(Result.success(authService.login(request),
                SuccessMessages.USER_LOGINED_SUCCESSFULLY.getMessage()));
    }
}
