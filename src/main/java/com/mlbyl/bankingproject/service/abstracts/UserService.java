package com.mlbyl.bankingproject.service.abstracts;

import com.mlbyl.bankingproject.dto.User_Dto.request.UserRegisterRequest;
import com.mlbyl.bankingproject.dto.User_Dto.request.UserUpdateRequest;
import com.mlbyl.bankingproject.dto.User_Dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponse> getAll();

    UserResponse getById(UUID id);

    UserResponse register(UserRegisterRequest request);

    UserResponse update(UserUpdateRequest request);

    void deleteById(UUID id);
}
