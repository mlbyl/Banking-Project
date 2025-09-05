package com.mlbyl.bankingproject.controller.payload;

import com.mlbyl.bankingproject.entity.Account;
import com.mlbyl.bankingproject.mapper.UserMapper;
import com.mlbyl.bankingproject.repository.AccountRepository;
import com.mlbyl.bankingproject.utilities.masker.MaskUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountResolver {
    private final AccountRepository accounts; // that's ok

    @Transactional(readOnly = true)
    public List<AccountPayload> resolveAccountsList(List<Account> accounts) {
        return accounts.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AccountPayload resolveAccount(Account account) {
        return Optional.ofNullable(account)
                .map(this::toResponse)
                .orElse(null);
    }

    private AccountPayload toResponse(Account account) {
        return new AccountPayload(
                account.getId(),
                MaskUtil.formattedIban(account.getIBAN()),
                account.getAccountType(),
                account.getAccountStatus(),
                account.getCurrency(),
                UserMapper.toUserInAccountResponse(account.getUser())
        );
    }
}
