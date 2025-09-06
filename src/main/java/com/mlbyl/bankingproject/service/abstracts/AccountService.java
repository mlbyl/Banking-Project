package com.mlbyl.bankingproject.service.abstracts;

import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountCreateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountUpdateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.response.AccountResponse;
import com.mlbyl.bankingproject.entity.Account;
import com.mlbyl.bankingproject.entity.User;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<Account> getAllAccountsByUser(User user);

    Account getAccountById(Long accountId, User user);

    Account create(AccountCreateRequest request, User user);

    Account update(AccountUpdateRequest request, Long accountId, User user);

    void deleteById(Long accountId, User user);
}
