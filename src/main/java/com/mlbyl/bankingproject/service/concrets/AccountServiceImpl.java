package com.mlbyl.bankingproject.service.concrets;

import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountCreateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.request.AccountUpdateRequest;
import com.mlbyl.bankingproject.dto.Account_Dto.response.AccountResponse;
import com.mlbyl.bankingproject.entity.Account;
import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.entity.enums.AccountStatus;
import com.mlbyl.bankingproject.entity.enums.UserStatus;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;


    @Override
    public List<AccountResponse> getAllAccountsByUser(UUID userId) {
        checkUserActive(getUser(userId));
        List<Account> accounts = accountRepository.findByUserId(userId);

        if (accounts.isEmpty()) {
            throw new NotFoundException(
                    ErrorMessages.ACCOUNTS_NOT_FOUND_WITH_USERID.format(userId),
                    ErrorCodes.ACCOUNT.name());
        }

        return AccountMapper.toResponse(accounts);
    }

    @Override
    public AccountResponse getAccountById(Long accountId, UUID userId) {
        userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.USER_NOT_FOUND_WITH_ID.format(userId),
                        ErrorCodes.USER.name()));

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND_WITH_ACCOUNTID.format(accountId),
                        ErrorCodes.ACCOUNT.name()));

        return AccountMapper.toResponse(account);
    }

    @Override
    public AccountResponse create(AccountCreateRequest request, UUID userId) {
        checkUserActive(getUser(userId));

        Account createdAccount = AccountMapper.toEntity(request, getUser(userId));

        //TODO Need to add verifcation to activate account status
        createdAccount.setAccountStatus(AccountStatus.INACTIVE);

        createdAccount.setAccountNumber(AccountUtils.generateAccountNumber());
        createdAccount.setBalance(BigDecimal.ZERO);

        //TODO Need to change bank code from developement enviroment
        createdAccount.setIBAN(AccountUtils.generateAccontIBAN("87654321"));

        Account savedAccount = accountRepository.save(createdAccount);

        return AccountMapper.toResponse(savedAccount);
    }

    @Override
    public AccountResponse update(AccountUpdateRequest request, Long accountId, UUID userId) {
        checkUserActive(getUser(userId));

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND_WITH_ACCOUNTID.format(accountId),
                        ErrorCodes.ACCOUNT.name()));

        Account updatedAccount = AccountMapper.updateEntity(request, account);
        Account savedAccount = accountRepository.save(updatedAccount);

        return AccountMapper.toResponse(savedAccount);
    }

    @Override
    public void deleteById(Long accountId, UUID userId) {
        checkUserActive(getUser(userId));

        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND_WITH_ACCOUNTID.format(accountId),
                        ErrorCodes.ACCOUNT.name()));

        accountRepository.deleteById(accountId);
    }


    private void checkUserActive(User user) {
        if (user.getUserStatus() != UserStatus.ACTIVE) {
            throw new BusinessException(ErrorMessages.USER_NOT_ACTIVE.format(user.getId()),
                    ErrorCodes.USER.name(),
                    HttpStatus.FORBIDDEN.value());
        }
    }

    private User getUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.USER_NOT_FOUND_WITH_ID.format(userId),
                        ErrorCodes.USER.name()));
        return user;
    }


}
