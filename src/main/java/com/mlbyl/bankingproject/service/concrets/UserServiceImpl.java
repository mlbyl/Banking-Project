package com.mlbyl.bankingproject.service.concrets;

import com.mlbyl.bankingproject.dto.User_Dto.request.UserRegisterRequest;
import com.mlbyl.bankingproject.dto.User_Dto.request.UserUpdateRequest;
import com.mlbyl.bankingproject.dto.User_Dto.response.UserResponse;
import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.entity.enums.UserStatus;
import com.mlbyl.bankingproject.entity.enums.Role;
import com.mlbyl.bankingproject.exception.BusinessException;
import com.mlbyl.bankingproject.exception.NotFoundException;
import com.mlbyl.bankingproject.mapper.UserMapper;
import com.mlbyl.bankingproject.repository.UserRepository;
import com.mlbyl.bankingproject.service.abstracts.UserService;
import com.mlbyl.bankingproject.utilities.constants.ErrorCodes;
import com.mlbyl.bankingproject.utilities.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return UserMapper.toResponse(users);
    }

    @Override
    public UserResponse getById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ErrorMessages.USER_NOT_FOUND_WITH_ID.format(id),
                ErrorCodes.USER.name()));
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse register(UserRegisterRequest request) {
        checkEmailExists(request.getEmail());
        User user = UserMapper.toEntity(request);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.ROLE_USER);
        user.setUserStatus(UserStatus.INACTIVE);
        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse update(UserUpdateRequest request, UUID id) {
        User existsUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ErrorMessages.USER_NOT_FOUND_WITH_ID.format(id),
                ErrorCodes.USER.name()));
        if (!existsUser.getEmail().equals(request.getEmail())) {
            checkEmailExists(request.getEmail());
        }
        User updatedUser = UserMapper.updateEntity(request, existsUser);
        User savedUser = userRepository.save(updatedUser);
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateLastLogin(UUID userId, LocalDateTime lastLogin) {
        User existUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.USER_NOT_FOUND_WITH_ID.format(userId),
                        ErrorCodes.USER.name()));

        existUser.setLastLogin(lastLogin);
        User savedUser = userRepository.save(existUser);
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse activateUser(UUID userId) {
        User existUser = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessages.USER_NOT_FOUND_WITH_ID.format(userId),
                        ErrorCodes.USER.name()));

        existUser.setUserStatus(UserStatus.ACTIVE);
        User savedUser = userRepository.save(existUser);
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public void deleteById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ErrorMessages.USER_NOT_FOUND_WITH_ID.format(id),
                ErrorCodes.USER.name()));
        userRepository.deleteById(id);
    }


    private void checkEmailExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(
                    ErrorMessages.EMAIL_ALREADY_EXISTS.format(email),
                    ErrorCodes.USER.name(), HttpStatus.CONFLICT.value());
        }
    }


}
