package com.mlbyl.bankingproject.mapper;

import com.mlbyl.bankingproject.dto.Transaction_Dto.request.TransactionDepositRequest;
import com.mlbyl.bankingproject.dto.Transaction_Dto.request.TransactionTransferRequest;
import com.mlbyl.bankingproject.dto.Transaction_Dto.request.TransactionWithdrawRequest;
import com.mlbyl.bankingproject.entity.Account;
import com.mlbyl.bankingproject.entity.Transaction;
import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.entity.enums.TransactionType;

public class TransactionMapper {
    public static Transaction toDepositEntity(TransactionDepositRequest request, User user, Account account) {
        return Transaction.builder()
                .amount(request.getAmount())
                .transactionType(TransactionType.DEPOSIT)
                .user(user)
                .toAccount(account)
                .fromAccount(null)
                .build();
    }

    public static Transaction toWithdrawEntity(TransactionWithdrawRequest request, User user, Account account) {
        return Transaction.builder()
                .amount(request.getAmount())
                .transactionType(TransactionType.WITHDRAW)
                .user(user)
                .fromAccount(account)
                .build();
    }


    public static Transaction toTransferEntity(TransactionTransferRequest request, User senderUser,
                                               Account senderAccount, Account recieverAccount) {
        return Transaction.builder()
                .amount(request.getAmount())
                .transactionType(TransactionType.TRANSFER)
                .user(senderUser)
                .fromAccount(senderAccount)
                .toAccount(recieverAccount)
                .build();
    }


}
