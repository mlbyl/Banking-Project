package com.mlbyl.bankingproject.mapper;

import com.mlbyl.bankingproject.dto.Account_Dto.response.AccountResponse;
import com.mlbyl.bankingproject.entity.Account;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {
    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                UserMapper.toUserInAccountResponse(account.getUser())
        );
    }

    public static List<AccountResponse> toResponse(List<Account> accounts) {
        if (accounts == null) return Collections.emptyList();
        return accounts.stream().map(account -> {
            return toResponse(account);
        }).collect(Collectors.toList());
    }
}
