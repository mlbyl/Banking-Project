package com.mlbyl.bankingproject.utilities.constants;

public enum ErrorMessages {

    // Specified error messages
    USER_NOT_FOUND_WITH_ID("User not found with id: "),

    USER_NOT_FOUND_WITH_USERNAME("User not found with username: "),

    USER_ALREADY_EXIST("User already exist with given details: "),

    USER_NOT_FOUND_WITH_EMAIL("User not found with email: "),

    EMAIL_ALREADY_EXISTS("Email already exists: "),


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
