package com.mlbyl.bankingproject.service.concrets;

import com.mlbyl.bankingproject.dto.Auth.request.LoginRequest;
import com.mlbyl.bankingproject.dto.Auth.response.AuthResponse;
import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.exception.NotFoundException;
import com.mlbyl.bankingproject.mapper.AccountMapper;
import com.mlbyl.bankingproject.repository.UserRepository;
import com.mlbyl.bankingproject.security.jwt.JwtService;
import com.mlbyl.bankingproject.security.model.CustomUserDetails;
import com.mlbyl.bankingproject.service.abstracts.AuthService;
import com.mlbyl.bankingproject.utilities.constants.ErrorCodes;
import com.mlbyl.bankingproject.utilities.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException(
                ErrorMessages.USER_NOT_FOUND_WITH_EMAIL.format(request.getEmail()), ErrorCodes.AUTH.name()));

        String token = jwtService.generateToken(new CustomUserDetails(user));
        user.setLastLogin(LocalDateTime.now());

        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .lastLogin(user.getLastLogin())
                .role(user.getRole())
                .accountStatus(user.getAccountStatus())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .accounts(AccountMapper.toResponse(user.getAccounts()))
                .email(user.getEmail())
                .token(token)
                .build();
    }
}
