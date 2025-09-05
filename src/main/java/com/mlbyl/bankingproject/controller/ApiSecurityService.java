package com.mlbyl.bankingproject.controller;

import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.entity.enums.UserStatus;
import com.mlbyl.bankingproject.exception.BusinessException;
import com.mlbyl.bankingproject.exception.NotFoundException;
import com.mlbyl.bankingproject.repository.UserRepository;
import com.mlbyl.bankingproject.security.jwt.JwtService;
import com.mlbyl.bankingproject.utilities.constants.ErrorCodes;
import com.mlbyl.bankingproject.utilities.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/*
    yes, why not;
    purpose-driven
 */
@Component
@RequiredArgsConstructor
public class ApiSecurityService {
    private final JwtService jwtService;
    private final UserRepository users;

    @Transactional(readOnly = true)
    public @NonNull User checkSomeStuff(String headerValue) {
        var userId = jwtService.extractUserId(headerValue.substring(7));

        var user = users.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.USER_NOT_FOUND_WITH_ID.format(userId),
                        ErrorCodes.USER.name()));


        if (user.getUserStatus() != UserStatus.ACTIVE) {
            throw new BusinessException(ErrorMessages.USER_NOT_ACTIVE.format(user.getId()),
                    ErrorCodes.USER.name(),
                    HttpStatus.FORBIDDEN.value());
        }

        return user;
    }
}
