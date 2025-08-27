package com.mlbyl.bankingproject.service.abstracts;

import com.mlbyl.bankingproject.dto.Auth.request.LoginRequest;
import com.mlbyl.bankingproject.dto.Auth.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}
