package com.mlbyl.bankingproject.utilities.constants;

public enum SuccessMessages {
    USER_RETRIEVED_SUCCESSFULLY("User retrieved successfully"),
    ALL_USERS_RETRIEVED_SUCCESSFULLY("All users retrieved successfully"),
    USER_CREATED_SUCCESSFULLY("User created successfully");

    private final String message;

    SuccessMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
