package com.mlbyl.bankingproject.dto.User_Dto.response;

import com.mlbyl.bankingproject.entity.enums.AccountStatus;
import com.mlbyl.bankingproject.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInAccountResponse {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private LocalDate dateOfBirth;
    private Role role;
    private AccountStatus accountStatus;
}
