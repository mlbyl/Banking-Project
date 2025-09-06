package com.mlbyl.bankingproject.service;

import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.exception.NotFoundException;
import com.mlbyl.bankingproject.repository.UserRepository;
import com.mlbyl.bankingproject.security.model.CustomUserDetails;
import com.mlbyl.bankingproject.utilities.constants.ErrorCodes;
import com.mlbyl.bankingproject.utilities.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ApiSecurityService {
    private final UserRepository userRepository;

    public User getAuthenticatedUser(CustomUserDetails userDetails) {
        UUID userId = userDetails.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.USER_NOT_FOUND_WITH_ID.format(userId),
                        ErrorCodes.USER.name()));
        return user;
    }
}
