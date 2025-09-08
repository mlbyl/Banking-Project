package com.mlbyl.bankingproject.mapper.resolver;

import com.mlbyl.bankingproject.dto.Transaction_Dto.response.TransactionResponse;
import com.mlbyl.bankingproject.entity.Transaction;
import com.mlbyl.bankingproject.mapper.AccountMapper;
import com.mlbyl.bankingproject.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionResolver {
    public TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .transactionStatus(transaction.getTransactionStatus())
                .createdAt(transaction.getCreatedAt())
                .senderAccountId(
                        transaction.getFromAccount() != null ? transaction.getFromAccount().getId() : null)
                .recieverAccountId(transaction.getToAccount() != null ? transaction.getToAccount().getId() : null)
                .build();

    }

    public List<TransactionResponse> toResponse(List<Transaction> transactions) {
        if (transactions == null) return Collections.emptyList();
        return transactions.stream().map(tr -> toResponse(tr)).collect(Collectors.toList());
    }

}
