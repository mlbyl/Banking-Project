package com.mlbyl.bankingproject.service.concrets;

import com.mlbyl.bankingproject.dto.User_Dto.request.UserRegisterRequest;
import com.mlbyl.bankingproject.dto.User_Dto.request.UserUpdateRequest;
import com.mlbyl.bankingproject.dto.User_Dto.response.UserResponse;
import com.mlbyl.bankingproject.entity.User;
import com.mlbyl.bankingproject.exception.NotFoundException;
import com.mlbyl.bankingproject.mapper.UserMapper;
import com.mlbyl.bankingproject.repository.UserRepository;
import com.mlbyl.bankingproject.service.abstracts.UserService;
import com.mlbyl.bankingproject.utilities.constants.ErrorCodes;
import com.mlbyl.bankingproject.utilities.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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
        return null;
    }

    @Override
    public UserResponse update(UserUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ErrorMessages.USER_NOT_FOUND_WITH_ID.format(id),
                ErrorCodes.USER.name()));
        userRepository.deleteById(id);
    }
}
