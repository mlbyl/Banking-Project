package com.mlbyl.bankingproject.controller;


import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountCreateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountUpdateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.response.AccountResponse;
import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.mapper.resolver.AccountResolver;
import com.mlbyl.bankingproject.security.model.CustomUserDetails;
import com.mlbyl.bankingproject.service.ApiSecurityService;
import com.mlbyl.bankingproject.service.abstracts.AccountService;
import com.mlbyl.bankingproject.utilities.constants.SuccessMessages;
import com.mlbyl.bankingproject.utilities.results.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final ApiSecurityService apiSecurityService;
    private final AccountResolver accountResolver;

    @GetMapping()
    public ResponseEntity<Result<List<AccountResponse>>> getAllAccountsByUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = apiSecurityService.getAuthenticatedUser(userDetails);

        var accounts = accountService.getAllAccountsByUser(user);
        var response = accountResolver.toResponse(accounts);

        return ResponseEntity.ok(Result.success(response,
                SuccessMessages.ALL_USER_ACCOUNTS_RETRIEVED_SUCCESSFULLY.getMessage()));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Result<AccountResponse>> getAccountById(@PathVariable Long accountId,
                                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = apiSecurityService.getAuthenticatedUser(userDetails);

        var account = accountService.getAccountById(accountId, user);
        var response = accountResolver.toResponse(account);

        return ResponseEntity.ok(Result.success(response,
                SuccessMessages.ACCOUNT_RETRIEVED_SUCCESSFULLY.getMessage()));
    }

    @PostMapping()
    public ResponseEntity<Result<AccountResponse>> create(@Valid @RequestBody AccountCreateRequest request,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = apiSecurityService.getAuthenticatedUser(userDetails);

        var account = accountService.create(request, user);
        var response = accountResolver.toResponse(account);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.success(response,
                        SuccessMessages.ACCOUNT_CREATED_SUCCESSFULLY.getMessage()));

    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Result<AccountResponse>> udpate(@Valid @RequestBody AccountUpdateRequest request,
                                                          @PathVariable Long accountId,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = apiSecurityService.getAuthenticatedUser(userDetails);

        var account = accountService.update(request, accountId, user);
        var response = accountResolver.toResponse(account);

        return ResponseEntity.ok(Result.success(response,
                SuccessMessages.ACCOUNT_UPDATED_SUCCESSFULLY.getMessage()));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Result<?>> deleteById(@PathVariable Long accountId,
                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = apiSecurityService.getAuthenticatedUser(userDetails);

        accountService.deleteById(accountId, user);
        return ResponseEntity.ok(Result.success(SuccessMessages.ACCOUNT_DELETED_SUCCESSFULLY.getMessage()));
    }

}
