package com.mlbyl.bankingproject.mapper;

import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountCreateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountUpdateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.response.AccountResponse;
import com.mlbyl.bankingproject.entity.Account;
import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.utilities.masker.MaskUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {
    public static AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .IBAN(MaskUtil.formattedIban(account.getIBAN()))
                .accountType(account.getAccountType())
                .accountStatus(account.getAccountStatus())
                .currency(account.getCurrency())
                .lastActivityDate(account.getLastActivityDate())
                .userId(account.getUser().getId())
                .build();

    }

    public static List<AccountResponse> toResponse(List<Account> accounts) {
        if (accounts == null) return Collections.emptyList();
        return accounts.stream().map(account -> {
            return toResponse(account);
        }).collect(Collectors.toList());
    }

    public static Account toEntity(AccountCreateRequest req, User user) {
        return Account.builder()
                .currency(req.getCurrency())
                .accountType(req.getAccountType())
                .user(user)
                .build();
    }

    public static Account updateEntity(AccountUpdateRequest req, Account account) {
        account.setCurrency(req.getCurrency());
        return account;
    }
}
