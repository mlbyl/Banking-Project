package com.mlbyl.bankingproject.service.concrets;

import com.mlbyl.bankingproject.dto.Transaction_Dto.request.TransactionDepositRequest;
import com.mlbyl.bankingproject.dto.Transaction_Dto.request.TransactionTransferRequest;
import com.mlbyl.bankingproject.dto.Transaction_Dto.request.TransactionWithdrawRequest;
import com.mlbyl.bankingproject.entity.Account;
import com.mlbyl.bankingproject.entity.Transaction;
import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.entity.enums.AccountStatus;
import com.mlbyl.bankingproject.entity.enums.AccountType;
import com.mlbyl.bankingproject.entity.enums.TransactionStatus;
import com.mlbyl.bankingproject.entity.enums.UserStatus;
import com.mlbyl.bankingproject.exception.AccessDeniedException;
import com.mlbyl.bankingproject.exception.BusinessException;
import com.mlbyl.bankingproject.exception.NotFoundException;
import com.mlbyl.bankingproject.mapper.TransactionMapper;
import com.mlbyl.bankingproject.repository.AccountRepository;
import com.mlbyl.bankingproject.repository.TransactionRepository;
import com.mlbyl.bankingproject.repository.UserRepository;
import com.mlbyl.bankingproject.service.abstracts.TransactionService;
import com.mlbyl.bankingproject.utilities.constants.ErrorCodes;
import com.mlbyl.bankingproject.utilities.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    public Transaction deposit(TransactionDepositRequest request, User user) {
        checkUserActive(user);
        Account account = getAccountByIBAN(request.getIban());

        checkAccountBelongsUser(account, user.getId());

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new BusinessException(ErrorMessages.ACCOUNT_NOT_ACTIVE.format(account.getId()),
                    ErrorCodes.ACCOUNT.name(), HttpStatus.LOCKED.value());
        }


        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(
                    ErrorMessages.TRANSACTION_AMOUNT_MUST_BE_GREATER_THAN_ZERO.name(),
                    ErrorCodes.TRANSACTION.name(), HttpStatus.BAD_REQUEST.value());
        }


        Transaction transaction = TransactionMapper.toDepositEntity(request, user, account);

        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction = transactionRepository.save(transaction);

        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        transaction.setTransactionStatus(TransactionStatus.COMPLETED);

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Transaction withdraw(TransactionWithdrawRequest request, User user) {
        checkUserActive(user);
        Account account = getAccountByIBAN(request.getIban());

        checkAccountBelongsUser(account, user.getId());

        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new BusinessException(ErrorMessages.ACCOUNT_NOT_ACTIVE.format(account.getId()),
                    ErrorCodes.ACCOUNT.name(), HttpStatus.LOCKED.value());
        }

        if (account.getAccountType() != AccountType.CHECKING && account.getAccountType() != AccountType.SAVINGS) {
            throw new BusinessException(
                    ErrorMessages.ONLY_ACCEPTED_ACCOUNT_TYPES_MAKE_TRANSACTION.format(account.getId()),
                    ErrorCodes.ACCOUNT.name(), HttpStatus.BAD_REQUEST.value());
        }


        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(
                    ErrorMessages.TRANSACTION_AMOUNT_MUST_BE_GREATER_THAN_ZERO.name(),
                    ErrorCodes.TRANSACTION.name(), HttpStatus.BAD_REQUEST.value());
        }
        if (request.getAmount().compareTo(account.getBalance()) > 0) {
            throw new BusinessException(ErrorMessages.NO_SUFFICIENT_BALANCE.name(),
                    ErrorCodes.ACCOUNT.name(), HttpStatus.BAD_REQUEST.value());
        }

        Transaction transaction = TransactionMapper.toWithdrawEntity(request, user, account);

        transaction.setTransactionStatus(TransactionStatus.PENDING);


        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        transaction.setTransactionStatus(TransactionStatus.COMPLETED);

        return transactionRepository.save(transaction);
    }

    @Transactional
    @Override
    public Transaction transfer(TransactionTransferRequest request, User user) {
        //Checking sender is active
        checkUserActive(user);

        User recieverUser = getRecieverUserByIBAN(request.getRecieverIBAN());

        //Checking reciever is active
        checkUserActive(recieverUser);

        Account senderAccount = getAccountByIBAN(request.getSenderIBAN());
        Account recieverAccount = getAccountByIBAN(request.getRecieverIBAN());

        checkAccountActive(senderAccount);
        checkAccountActive(recieverAccount);

        if (senderAccount.getId().equals(recieverAccount.getId())) {
            throw new BusinessException(
                    ErrorMessages.CANNOT_TRANSFER_TO_SAME_ACCOUNT.format(senderAccount.getId()),
                    ErrorCodes.TRANSACTION.name(), HttpStatus.BAD_REQUEST.value());
        }

        //Checking sender account is belongs to user
        checkAccountBelongsUser(senderAccount, user.getId());
        //Checking reciever account is belongs to reciever user
        checkAccountBelongsUser(recieverAccount, recieverUser.getId());


        if (senderAccount.getAccountType() != AccountType.CHECKING && senderAccount.getAccountType() != AccountType.SAVINGS) {
            throw new BusinessException(
                    ErrorMessages.ONLY_ACCEPTED_ACCOUNT_TYPES_MAKE_TRANSACTION.format(senderAccount.getId()),
                    ErrorCodes.ACCOUNT.name(), HttpStatus.BAD_REQUEST.value());
        }


        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(
                    ErrorMessages.TRANSACTION_AMOUNT_MUST_BE_GREATER_THAN_ZERO.format(senderAccount.getId()),
                    ErrorCodes.TRANSACTION.name(), HttpStatus.BAD_REQUEST.value());
        }
        if (request.getAmount().compareTo(senderAccount.getBalance()) > 0) {
            throw new BusinessException(ErrorMessages.NO_SUFFICIENT_BALANCE.format(senderAccount.getId()),
                    ErrorCodes.ACCOUNT.name(), HttpStatus.BAD_REQUEST.value());
        }

        Transaction transaction =
                TransactionMapper.toTransferEntity(request, user, senderAccount, recieverAccount);

        transaction.setTransactionStatus(TransactionStatus.PENDING);


        senderAccount.setBalance(senderAccount.getBalance().subtract(request.getAmount()));
        recieverAccount.setBalance(recieverAccount.getBalance().add(request.getAmount()));

        accountRepository.save(senderAccount);
        accountRepository.save(recieverAccount);


        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        return transactionRepository.save(transaction);
    }

    @Transactional
    @Override
    public Transaction cancelTransaction(Long transactionId, User user) {
        checkUserActive(user);

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.TRANSACTION_NOT_FOUND_WITH_ID.format(transactionId),
                        ErrorCodes.TRANSACTION.name()));

        if (transaction.getTransactionStatus() != TransactionStatus.COMPLETED) {
            throw new BusinessException(
                    ErrorMessages.ONLY_COMPLETED_TRANSACTION_CAN_BE_CANCELLED.format(transactionId),
                    ErrorCodes.TRANSACTION.name(), HttpStatus.BAD_REQUEST.value());
        }

        Account senderAccount = transaction.getFromAccount();
        Account recieverAccount = transaction.getToAccount();

        checkAccountBelongsUser(senderAccount, user.getId());

        checkAccountActive(senderAccount);
        checkAccountActive(recieverAccount);


        if (recieverAccount.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new BusinessException(ErrorMessages.NO_SUFFICIENT_BALANCE.format(recieverAccount.getId()),
                    ErrorCodes.ACCOUNT.name(), HttpStatus.BAD_REQUEST.value());
        }

        recieverAccount.setBalance(recieverAccount.getBalance().subtract(transaction.getAmount()));
        senderAccount.setBalance(senderAccount.getBalance().add(transaction.getAmount()));

        transaction.setTransactionStatus(TransactionStatus.CANCELLED);

        accountRepository.save(senderAccount);
        accountRepository.save(recieverAccount);


        return transactionRepository.save(transaction);
    }

    @Transactional
    @Override
    public Transaction getTransactionById(Long transactionId, User user) {
        checkUserActive(user);

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.TRANSACTION_NOT_FOUND_WITH_ID.format(transactionId),
                        ErrorCodes.TRANSACTION.name()));


        return transaction;
    }

    @Transactional
    @Override
    public List<Transaction> getTransactionsByUserId(User user) {
        checkUserActive(user);

        List<Transaction> transactions = transactionRepository.findByUserId(user.getId());

        return transactions;
    }

    @Transactional
    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId, User user) {
        checkUserActive(user);

        Account account = getAccountById(accountId);

        checkAccountActive(account);
        checkAccountBelongsUser(account, user.getId());

        List<Transaction> transactions = transactionRepository.findByfromAccountId(accountId);

        return transactions;
    }

    private void checkUserActive(User user) {
        if (user.getUserStatus() != UserStatus.ACTIVE) {
            throw new BusinessException(ErrorMessages.USER_NOT_ACTIVE.format(user.getId()),
                    ErrorCodes.USER.name(),
                    HttpStatus.FORBIDDEN.value());
        }
    }

    private Account getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND_WITH_ACCOUNTID.format(accountId),
                        ErrorCodes.ACCOUNT.name()));
        return account;
    }

    private Account getAccountByIBAN(String IBAN) {
        Account account = accountRepository.findByIBAN(IBAN).orElseThrow(
                () -> new NotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND.name(),
                        ErrorCodes.ACCOUNT.name()));
        return account;
    }

    private void checkAccountBelongsUser(Account account, UUID userId) {
        if (account.getUser().getId() != userId) {
            throw new AccessDeniedException(
                    ErrorMessages.ACCESS_DENIED_ACCOUNT_NOT_BELONGS_USER.format(userId),
                    ErrorCodes.ACCESS_DENIED.name());
        }
    }

    private void checkAccountActive(Account account) {
        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new BusinessException(ErrorMessages.ACCOUNT_NOT_ACTIVE.format(account.getId()),
                    ErrorCodes.ACCOUNT.name(), HttpStatus.LOCKED.value());
        }
    }

    private User getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessages.USER_NOT_FOUND_WITH_ID.format(id),
                        ErrorCodes.USER.name()));

        return user;
    }

    private User getRecieverUserByIBAN(String recieverIBAN) {
        Account account = getAccountByIBAN(recieverIBAN);
        checkAccountActive(account);
        User user = getUserById(account.getUser().getId());
        return user;
    }
}
