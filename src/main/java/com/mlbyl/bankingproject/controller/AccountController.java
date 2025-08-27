package com.mlbyl.bankingproject.controller;


import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountCreateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountUpdateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.response.AccountResponse;
import com.mlbyl.bankingproject.security.jwt.JwtService;
import com.mlbyl.bankingproject.service.abstracts.AccountService;
import com.mlbyl.bankingproject.utilities.constants.SuccessMessages;
import com.mlbyl.bankingproject.utilities.results.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final JwtService jwtService;

    @GetMapping()
    public ResponseEntity<Result<List<AccountResponse>>> getAllAccountsByUser(
            @RequestHeader("Authorization") String authHeader) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));

        return ResponseEntity.ok(Result.success(accountService.getAllAccountsByUser(userId),
                SuccessMessages.ALL_USER_ACCOUNTS_RETRIEVED_SUCCESSFULLY.getMessage()));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Result<AccountResponse>> getAccountById(@PathVariable Long accountId,
                                                                  @RequestHeader("Authorization") String authHeader) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));

        return ResponseEntity.ok(Result.success(accountService.getAccountById(accountId, userId),
                SuccessMessages.ACCOUNT_RETRIEVED_SUCCESSFULLY.getMessage()));
    }

    @PostMapping()
    public ResponseEntity<Result<AccountResponse>> create(@Valid @RequestBody AccountCreateRequest request,
                                                          @RequestHeader("Authorization") String authHeader) {

        UUID userId = jwtService.extractUserId(authHeader.substring(7));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.success(accountService.create(request, userId),
                        SuccessMessages.ACCOUNT_CREATED_SUCCESSFULLY.getMessage()));

    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Result<AccountResponse>> udpate(@Valid @RequestBody AccountUpdateRequest request,
                                                          @PathVariable Long accountId,
                                                          @RequestHeader("Authorization") String authHeader) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));
        return ResponseEntity.ok(Result.success(accountService.update(request, accountId, userId),
                SuccessMessages.ACCOUNT_UPDATED_SUCCESSFULLY.getMessage()));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Result<?>> deleteById(@PathVariable Long accountId,
                                                @RequestHeader("Authorization") String authHeader) {
        UUID userId = jwtService.extractUserId(authHeader.substring(7));
        accountService.deleteById(accountId, userId);
        return ResponseEntity.ok(Result.success(SuccessMessages.ACCOUNT_DELETED_SUCCESSFULLY.getMessage()));
    }

}
