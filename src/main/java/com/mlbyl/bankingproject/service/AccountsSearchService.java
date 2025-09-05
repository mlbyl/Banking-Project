package com.mlbyl.bankingproject.service;

import com.mlbyl.bankingproject.dto.Account_Dto.response.AccountResponse;
import com.mlbyl.bankingproject.entity.Account;
import com.mlbyl.bankingproject.exception.NotFoundException;
import com.mlbyl.bankingproject.mapper.AccountMapper;
import com.mlbyl.bankingproject.repository.AccountRepository;
import com.mlbyl.bankingproject.repository.UserRepository;
import com.mlbyl.bankingproject.utilities.constants.ErrorCodes;
import com.mlbyl.bankingproject.utilities.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/*
    you don't need an interface here, it doesn't provide any value
    also, your services need to be purpose-driven,
    not just "everything-about-Account dump"
 */
@Component
@RequiredArgsConstructor
public class AccountsSearchService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Account> getAllAccountsByUser(UUID userId) {
        var accounts = accountRepository.findByUserId(userId);

        if (accounts.isEmpty()) {
            throw new NotFoundException(
                    ErrorMessages.ACCOUNTS_NOT_FOUND_WITH_USERID.format(userId),
                    ErrorCodes.ACCOUNT.name());
        }

        return accounts;

    }

    @Transactional(readOnly = true)
    public Account getAccountById(Long accountId, UUID userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.USER_NOT_FOUND_WITH_ID.format(userId),
                        ErrorCodes.USER.name()));

        return accountRepository.findById(accountId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND_WITH_ACCOUNTID.format(accountId),
                        ErrorCodes.ACCOUNT.name()));
    }
}
