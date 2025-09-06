package com.mlbyl.bankingproject.mapper.resolver;

import com.mlbyl.bankingproject.dto.Account_Dto.response.AccountResponse;
import com.mlbyl.bankingproject.entity.Account;
import com.mlbyl.bankingproject.mapper.UserMapper;
import com.mlbyl.bankingproject.utilities.masker.MaskUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountResolver {
    @Transactional(readOnly = true)
    public AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                MaskUtil.formattedIban(account.getIBAN()),
                account.getAccountType(),
                account.getAccountStatus(),
                account.getCurrency(),
                UserMapper.toUserInAccountResponse(account.getUser())
        );
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> toResponse(List<Account> accounts) {
        if (accounts == null) return Collections.emptyList();
        return accounts.stream().map(account -> {
            return toResponse(account);
        }).collect(Collectors.toList());
    }
}
