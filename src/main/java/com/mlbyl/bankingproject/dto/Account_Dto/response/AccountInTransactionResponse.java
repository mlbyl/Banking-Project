package com.mlbyl.bankingproject.dto.Account_Dto.response;

import com.mlbyl.bankingproject.entity.enums.AccountStatus;
import com.mlbyl.bankingproject.entity.enums.AccountType;
import com.mlbyl.bankingproject.entity.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountInTransactionResponse {
    private Long accountId;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private Currency currency;
}
