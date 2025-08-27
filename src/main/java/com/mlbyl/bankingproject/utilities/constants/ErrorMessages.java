package com.mlbyl.bankingproject.utilities.constants;

public enum ErrorMessages {

    // Specified error messages
    USER_NOT_FOUND_WITH_ID("User not found with id: "),

    USER_NOT_FOUND_WITH_USERNAME("User not found with username: "),

    USER_ALREADY_EXIST("User already exist with given details: "),

    USER_NOT_FOUND_WITH_EMAIL("User not found with email: "),

    EMAIL_ALREADY_EXISTS("Email already exists: "),

    USER_NOT_ACTIVE("User not active"),


    ACCOUNTS_NOT_FOUND_WITH_USERID("Accounts not found with user id: "),
    ACCOUNT_NOT_FOUND_WITH_ACCOUNTID("Account not found with account id: "),


    DATABASE_ERROR_OCCURED("Database error occured"),
    VALIDATION_ERROR_OCCURED("Validation error occured");


    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String format(Object arg) {
        return getMessage() + arg;
    }


}
