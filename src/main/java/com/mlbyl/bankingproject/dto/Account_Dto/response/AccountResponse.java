package com.mlbyl.bankingproject.dto.Account_Dto.response;

import com.mlbyl.bankingproject.dto.User_Dto.response.UserInAccountResponse;
import com.mlbyl.bankingproject.entity.enums.AccountStatus;
import com.mlbyl.bankingproject.entity.enums.AccountType;
import com.mlbyl.bankingproject.entity.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Long id;
    private String IBAN;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private Currency currency;
    private UserInAccountResponse user;
}
