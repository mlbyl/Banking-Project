package com.mlbyl.bankingproject.controller;

import com.mlbyl.bankingproject.dto.User_Dto.response.UserResponse;
import com.mlbyl.bankingproject.service.abstracts.UserService;
import com.mlbyl.bankingproject.utilities.constants.SuccessMessages;
import com.mlbyl.bankingproject.utilities.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Result<List<UserResponse>>> getAll() {
        return ResponseEntity.ok(Result.success(userService.getAll(), SuccessMessages.ALL_USERS_RETRIEVED_SUCCESSFULLY.getMessage()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<UserResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(Result.success(userService.getById(id), SuccessMessages.USER_RETRIEVED_SUCCESSFULLY.getMessage()));
    }
}
