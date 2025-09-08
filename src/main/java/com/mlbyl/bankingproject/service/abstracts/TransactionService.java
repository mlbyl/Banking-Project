package com.mlbyl.bankingproject.service.abstracts;

import com.mlbyl.bankingproject.dto.Transaction_Dto.request.TransactionDepositRequest;
import com.mlbyl.bankingproject.dto.Transaction_Dto.request.TransactionTransferRequest;
import com.mlbyl.bankingproject.dto.Transaction_Dto.request.TransactionWithdrawRequest;
import com.mlbyl.bankingproject.entity.Transaction;
import com.mlbyl.bankingproject.entity.User;

import java.util.List;

public interface TransactionService {
    Transaction deposit(TransactionDepositRequest request, User user);

    Transaction withdraw(TransactionWithdrawRequest request, User user);

    Transaction transfer(TransactionTransferRequest request, User user);


    Transaction cancelTransaction(Long transactionId, User user);

    Transaction getTransactionById(Long transactionId, User user);

    List<Transaction> getTransactionsByUserId(User user);

    List<Transaction> getTransactionsByAccountId(Long accountId,User user);
}
