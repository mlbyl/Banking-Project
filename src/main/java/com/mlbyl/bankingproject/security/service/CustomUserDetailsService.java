package com.mlbyl.bankingproject.security.service;

import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.exception.NotFoundException;
import com.mlbyl.bankingproject.repository.UserRepository;
import com.mlbyl.bankingproject.security.model.CustomUserDetails;
import com.mlbyl.bankingproject.utilities.constants.ErrorCodes;
import com.mlbyl.bankingproject.utilities.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException(
                        ErrorMessages.USER_NOT_FOUND_WITH_EMAIL.format(email),
                        ErrorCodes.USER.name()));

        return new CustomUserDetails(user);
    }
}
