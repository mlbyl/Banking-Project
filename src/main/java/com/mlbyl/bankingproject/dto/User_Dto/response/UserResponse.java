package com.mlbyl.bankingproject.dto.User_Dto.response;

import com.mlbyl.bankingproject.dto.Account_Dto.response.AccountResponse;
import com.mlbyl.bankingproject.entity.enums.UserStatus;
import com.mlbyl.bankingproject.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private LocalDate dateOfBirth;
    private Role role;
    private UserStatus userStatus;
    private Integer phoneNumber;
    private LocalDateTime lastLogin;
    private List<AccountResponse> accounts;

}
