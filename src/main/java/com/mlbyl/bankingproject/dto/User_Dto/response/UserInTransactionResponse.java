package com.mlbyl.bankingproject.dto.User_Dto.response;

import com.mlbyl.bankingproject.entity.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInTransactionResponse {
    private UUID userId;
    private String name;
    private String surname;
    private String email;
    private UserStatus userStatus;
}
