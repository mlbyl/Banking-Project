package com.mlbyl.bankingproject.mapper;

import com.mlbyl.bankingproject.dto.User_Dto.request.UserRegisterRequest;
import com.mlbyl.bankingproject.dto.User_Dto.request.UserUpdateRequest;
import com.mlbyl.bankingproject.dto.User_Dto.response.UserInAccountResponse;
import com.mlbyl.bankingproject.dto.User_Dto.response.UserInTransactionResponse;
import com.mlbyl.bankingproject.dto.User_Dto.response.UserResponse;
import com.mlbyl.bankingproject.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static User toEntity(UserRegisterRequest req) {
        return User
                .builder()
                .name(req.getName())
                .surname(req.getSurname())
                .email(req.getEmail())
                .password(req.getPassword())
                .phoneNumber(req.getPhoneNumber())
                .dateOfBirth(req.getDateOfBirth())
                .build();
    }

    public static User updateEntity(UserUpdateRequest req, User user) {
        user.setName(req.getName());
        user.setSurname(req.getSurname());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setPhoneNumber(req.getPhoneNumber());
        user.setDateOfBirth(req.getDateOfBirth());

        return user;
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getRole(),
                user.getUserStatus(),
                user.getPhoneNumber(),
                user.getLastLogin(),
                AccountMapper.toResponse(user.getAccounts())
        );
    }

    public static List<UserResponse> toResponse(List<User> users) {
        if (users == null) return Collections.emptyList();

        return users.stream().map(user -> {
            return toResponse(user);
        }).collect(Collectors.toList());
    }

    public static UserInAccountResponse toUserInAccountResponse(User user) {
        return new UserInAccountResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getRole(),
                user.getUserStatus()
        );
    }


}
