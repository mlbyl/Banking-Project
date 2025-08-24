package com.mlbyl.bankingproject.dto.Account_Dto.response;

import com.mlbyl.bankingproject.dto.User_Dto.response.UserInAccountResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Long id;
    private UserInAccountResponse user;
}
