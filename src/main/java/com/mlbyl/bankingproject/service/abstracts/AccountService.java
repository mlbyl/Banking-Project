package com.mlbyl.bankingproject.service.abstracts;

import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountCreateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountUpdateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.response.AccountResponse;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<AccountResponse> getAllAccountsByUser(UUID userId);

    AccountResponse getAccountById(Long accountId, UUID userId);

    AccountResponse create(AccountCreateRequest request, UUID userId);

    AccountResponse update(AccountUpdateRequest request, Long accountId, UUID userId);

    void deleteById(Long accountId, UUID userId);
}
