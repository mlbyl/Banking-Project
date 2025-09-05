package com.mlbyl.bankingproject.controller.payload;

import com.mlbyl.bankingproject.dto.User_Dto.response.UserInAccountResponse;
import com.mlbyl.bankingproject.entity.enums.AccountStatus;
import com.mlbyl.bankingproject.entity.enums.AccountType;
import com.mlbyl.bankingproject.entity.enums.Currency;

public record AccountPayload(
        long id,
        String IBAN,
        AccountType accountType,
        AccountStatus accountStatus,
        Currency currency,
        UserInAccountResponse user
) {
    // created a new record instead of reusing AccountResponse
    // just to show in which package I would actually place this
}
