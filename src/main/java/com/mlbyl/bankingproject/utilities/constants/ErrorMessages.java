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
    ACCOUNT_NOT_FOUND("Account not found"),
    ACCOUNT_NOT_ACTIVE("Account not active"),

    ONLY_ACCEPTED_ACCOUNT_TYPES_MAKE_TRANSACTION(
            "Only Savings and Checking type Accounts can make a transaction"),
    TRANSACTION_AMOUNT_MUST_BE_GREATER_THAN_ZERO("Amount must be greater than 0"),
    TRANSACTION_NOT_FOUND_WITH_ID("Transaction not found"),
    ONLY_COMPLETED_TRANSACTION_CAN_BE_CANCELLED("Only completed transactions can be cancelled"),


    ACCESS_DENIED_ACCOUNT_NOT_BELONGS_USER("Access denied Account not belongs user: "),
    ACCESS_DENIED_ACCOUNT_PERMISSION_NOT_ABLE_TO_DO_OPERATION(
            "Access denied Account permission has not able to do this operation: "),

    NO_SUFFICIENT_BALANCE("No sufficient balance"),

    CANNOT_TRANSFER_TO_SAME_ACCOUNT("Cannot transfer to same account"),


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
