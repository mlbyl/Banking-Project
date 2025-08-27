package com.mlbyl.bankingproject.dto.Auth.response;

import com.mlbyl.bankingproject.dto.Account_Dto.response.AccountResponse;
import com.mlbyl.bankingproject.entity.enums.UserStatus;
import com.mlbyl.bankingproject.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private Role role;
    private UserStatus userStatus;
    private Integer phoneNumber;
    private LocalDateTime lastLogin;
    private String token;
    private List<AccountResponse> accounts;

}
