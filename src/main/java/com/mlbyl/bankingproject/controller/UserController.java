package com.mlbyl.bankingproject.controller;

import com.mlbyl.bankingproject.dto.User_Dto.request.UserRegisterRequest;
import com.mlbyl.bankingproject.dto.User_Dto.request.UserUpdateRequest;
import com.mlbyl.bankingproject.dto.User_Dto.response.UserResponse;
import com.mlbyl.bankingproject.service.abstracts.UserService;
import com.mlbyl.bankingproject.utilities.constants.SuccessMessages;
import com.mlbyl.bankingproject.utilities.results.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Result<List<UserResponse>>> getAll() {
        return ResponseEntity.ok(
                Result.success(userService.getAll(),
                        SuccessMessages.ALL_USERS_RETRIEVED_SUCCESSFULLY.getMessage()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<UserResponse>> getById(@Valid @PathVariable UUID id) {
        return ResponseEntity.ok(
                Result.success(userService.getById(id),
                        SuccessMessages.USER_RETRIEVED_SUCCESSFULLY.getMessage()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<?>> deleteById(@Valid @PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.ok(Result.success(SuccessMessages.USER_DELETED_SUCCESSFULLY.getMessage()));
    }

    @PostMapping()
    public ResponseEntity<Result<UserResponse>> register(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.success(userService.register(request),
                        SuccessMessages.USER_CREATED_SUCCESSFULLY.getMessage()));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Result<UserResponse>> update(@PathVariable UUID id,
                                                       @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(Result.success(
                userService.update(request, id),
                SuccessMessages.USER_UPDATED_SUCCESSFULLY.getMessage()));
    }
}
