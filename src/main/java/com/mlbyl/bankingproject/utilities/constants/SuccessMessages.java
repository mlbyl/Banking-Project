package com.mlbyl.bankingproject.utilities.constants;

public enum SuccessMessages {
    USER_RETRIEVED_SUCCESSFULLY("User retrieved successfully"),
    ALL_USERS_RETRIEVED_SUCCESSFULLY("All users retrieved successfully"),
    USER_CREATED_SUCCESSFULLY("User created successfully"),
    USER_DELETED_SUCCESSFULLY("User deleted successfully"),
    USER_UPDATED_SUCCESSFULLY("User successfully updated"),
    USER_LOGINED_SUCCESSFULLY("User logined successfully"),

    ALL_USER_ACCOUNTS_RETRIEVED_SUCCESSFULLY("All user accounts retrieved successfully"),
    ACCOUNT_RETRIEVED_SUCCESSFULLY("Account retrieved successfully"),
    ACCOUNT_CREATED_SUCCESSFULLY("Account created successfull"),
    ACCOUNT_UPDATED_SUCCESSFULLY("Account updated successfully"),
    ACCOUNT_DELETED_SUCCESSFULLY("Account deleted successfully");

    private final String message;

    SuccessMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
