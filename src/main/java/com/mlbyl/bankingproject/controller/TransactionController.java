package com.mlbyl.bankingproject.controller;


import com.mlbyl.bankingproject.dto.Transaction_Dto.request.TransactionDepositRequest;
import com.mlbyl.bankingproject.dto.Transaction_Dto.request.TransactionTransferRequest;
import com.mlbyl.bankingproject.dto.Transaction_Dto.request.TransactionWithdrawRequest;
import com.mlbyl.bankingproject.dto.Transaction_Dto.response.TransactionResponse;
import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.mapper.resolver.TransactionResolver;
import com.mlbyl.bankingproject.security.model.CustomUserDetails;
import com.mlbyl.bankingproject.service.ApiSecurityService;
import com.mlbyl.bankingproject.service.abstracts.TransactionService;
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
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final ApiSecurityService apiSecurityService;
    private final TransactionResolver transactionResolver;


    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<Result<TransactionResponse>> withdraw(
            @Valid @RequestBody TransactionWithdrawRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        User user = apiSecurityService.getAuthenticatedUser(userDetails);
        var transaction = transactionService.withdraw(request, user);

        var response = transactionResolver.toResponse(transaction);


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.success(response, SuccessMessages.TRANSACTION_CREATED_SUCCESSFULLY.getMessage()));
    }


    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<Result<TransactionResponse>> deposit(
            @Valid @RequestBody TransactionDepositRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = apiSecurityService.getAuthenticatedUser(userDetails);
        var transaction = transactionService.deposit(request, user);

        var response = transactionResolver.toResponse(transaction);

        System.out.println(response);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.success(response, SuccessMessages.TRANSACTION_CREATED_SUCCESSFULLY.getMessage()));
    }


    @PostMapping("/transfer/{accountId}")
    public ResponseEntity<Result<TransactionResponse>> transfer(
            @Valid @RequestBody TransactionTransferRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = apiSecurityService.getAuthenticatedUser(userDetails);
        var transaction = transactionService.transfer(request, user);
        var response = transactionResolver.toResponse(transaction);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.success(response, SuccessMessages.TRANSACTION_CREATED_SUCCESSFULLY.getMessage()));
    }

    @PostMapping("/cancel/{transactionId}")
    public ResponseEntity<Result<TransactionResponse>> cancelTransaction(
            @PathVariable Long transactionId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = apiSecurityService.getAuthenticatedUser(userDetails);
        var transaction = transactionService.cancelTransaction(transactionId, user);
        var response = transactionResolver.toResponse(transaction);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.success(response, SuccessMessages.TRANSACTION_CREATED_SUCCESSFULLY.getMessage()));
    }


    @GetMapping("/{transactionId}")
    public ResponseEntity<Result<TransactionResponse>> getTransactionById(@PathVariable Long transactionId,
                                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = apiSecurityService.getAuthenticatedUser(userDetails);
        var transaction = transactionService.getTransactionById(transactionId, user);
        var response = transactionResolver.toResponse(transaction);

        return ResponseEntity.ok(
                Result.success(response, SuccessMessages.ALL_TRANSACTIONS_RETRIEVED_SUCCESSFULLY.getMessage()));

    }

    @GetMapping("/getallbyuserid")
    public ResponseEntity<Result<List<TransactionResponse>>> getTransactionsByUserId(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = apiSecurityService.getAuthenticatedUser(userDetails);
        var transactions = transactionService.getTransactionsByUserId(user);
        var response = transactionResolver.toResponse(transactions);

        return ResponseEntity.ok(
                Result.success(response, SuccessMessages.ALL_TRANSACTIONS_RETRIEVED_SUCCESSFULLY.getMessage()));
    }

    @GetMapping("/getallbyaccountid/{accountId}")
    public ResponseEntity<Result<List<TransactionResponse>>> getTransactionsByAccountId(
            @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long accountId) {

        User user = apiSecurityService.getAuthenticatedUser(userDetails);
        var transactions = transactionService.getTransactionsByAccountId(accountId, user);
        var response = transactionResolver.toResponse(transactions);

        return ResponseEntity.ok(
                Result.success(response, SuccessMessages.ALL_TRANSACTIONS_RETRIEVED_SUCCESSFULLY.getMessage()));
    }


}
