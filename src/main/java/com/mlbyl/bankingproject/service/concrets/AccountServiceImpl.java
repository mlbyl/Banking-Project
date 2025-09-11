package com.mlbyl.bankingproject.service.concrets;

import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountCreateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountUpdateRequest;
import com.mlbyl.bankingproject.entity.Account;
import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.entity.enums.AccountStatus;
import com.mlbyl.bankingproject.entity.enums.OperationType;
import com.mlbyl.bankingproject.entity.enums.UserStatus;
import com.mlbyl.bankingproject.exception.AccessDeniedException;
import com.mlbyl.bankingproject.exception.BusinessException;
import com.mlbyl.bankingproject.exception.NotFoundException;
import com.mlbyl.bankingproject.mapper.AccountMapper;
import com.mlbyl.bankingproject.repository.AccountRepository;
import com.mlbyl.bankingproject.repository.UserRepository;
import com.mlbyl.bankingproject.service.abstracts.AccountService;
import com.mlbyl.bankingproject.utilities.account_utilities.AccountUtils;
import com.mlbyl.bankingproject.utilities.constants.ErrorCodes;
import com.mlbyl.bankingproject.utilities.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;


    @Override
    public List<Account> getAllAccountsByUser(User user) {
        checkUserActive(user);
        List<Account> accounts = accountRepository.findByUserId(user.getId());

        if (accounts.isEmpty()) {
            throw new NotFoundException(
                    ErrorMessages.ACCOUNTS_NOT_FOUND_WITH_USERID.format(user.getId()),
                    ErrorCodes.ACCOUNT.name());
        }

        return accounts;
    }

    @Override
    public Account getAccountById(Long accountId, User user) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND_WITH_ACCOUNTID.format(accountId),
                        ErrorCodes.ACCOUNT.name()));

        checkAccountBelongsUser(account, user.getId());

        return account;
    }

    @Transactional
    @Override
    public Account create(AccountCreateRequest request, User user) {
        checkUserActive(user);

        Account createdAccount = AccountMapper.toEntity(request, user);

        //TODO Need to add verifcation to activate account status
        createdAccount.setAccountStatus(AccountStatus.PENDING_APPROVAL);

        createdAccount.setAccountNumber(AccountUtils.generateAccountNumber());
        createdAccount.setBalance(BigDecimal.ZERO);

        //TODO Need to change bank code from developement enviroment
        createdAccount.setIBAN(AccountUtils.generateAccountIBAN("87654321", createdAccount.getAccountNumber()));

        Account savedAccount = accountRepository.save(createdAccount);

        return savedAccount;
    }

    @Transactional
    @Override
    public Account update(AccountUpdateRequest request, Long accountId, User user) {
        checkUserActive(user);

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND_WITH_ACCOUNTID.format(accountId),
                        ErrorCodes.ACCOUNT.name()));

        isAccountAbleToOperate(account, OperationType.UPDATE);
        checkAccountBelongsUser(account, user.getId());

        Account updatedAccount = AccountMapper.updateEntity(request, account);
        updatedAccount.setLastActivityDate(LocalDateTime.now());

        Account savedAccount = accountRepository.save(updatedAccount);

        return savedAccount;
    }

    @Transactional
    @Override
    public void deleteById(Long accountId, User user) {
        checkUserActive(user);

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND_WITH_ACCOUNTID.format(accountId),
                        ErrorCodes.ACCOUNT.name()));

        isAccountAbleToOperate(account, OperationType.DELETE);
        checkAccountBelongsUser(account, user.getId());

        account.setLastActivityDate(LocalDateTime.now());
        account.setAccountStatus(AccountStatus.CLOSED);
//        accountRepository.deleteById(accountId);
    }


    private void checkUserActive(User user) {
        if (user.getUserStatus() != UserStatus.ACTIVE) {
            throw new BusinessException(ErrorMessages.USER_NOT_ACTIVE.format(user.getId()),
                    ErrorCodes.USER.name(),
                    HttpStatus.LOCKED.value());
        }
    }

    private void isAccountAbleToOperate(Account account, OperationType operationType) {
        switch (operationType) {
            case UPDATE:
                if (account.getAccountStatus() != AccountStatus.ACTIVE) {
                    throw new AccessDeniedException(
                            ErrorMessages.ACCESS_DENIED_ACCOUNT_PERMISSION_NOT_ABLE_TO_DO_OPERATION.format(
                                    account.getId()),
                            ErrorCodes.ACCESS_DENIED.name());
                }
                break;
            case DELETE:
                if (account.getAccountStatus() != AccountStatus.ACTIVE && account.getAccountStatus() != AccountStatus.INACTIVE) {
                    throw new AccessDeniedException(
                            ErrorMessages.ACCESS_DENIED_ACCOUNT_PERMISSION_NOT_ABLE_TO_DO_OPERATION.format(
                                    account.getId()), ErrorCodes.ACCESS_DENIED.name());
                }
                break;
        }
    }

    private void checkAccountBelongsUser(Account account, UUID userId) {
        if (account.getUser().getId() != userId) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED_ACCOUNT_NOT_BELONGS_USER.format(userId),
                    ErrorCodes.ACCESS_DENIED.name());
        }
    }


}
