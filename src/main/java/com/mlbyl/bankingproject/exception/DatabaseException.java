package com.mlbyl.bankingproject.exception;

import com.mlbyl.bankingproject.utilities.constants.ErrorMessages;

public class DatabaseException extends BaseException {
    public DatabaseException(String message, String errorCode, int statusCode) {
        super(extractErrorMessage(message), errorCode, statusCode);
    }

    private static String extractErrorMessage(String message) {
        if (message.contains("Detail")) {
            return message.substring(message.indexOf("Detail:"));
        } else if (message.contains("\\")) {
            return message.substring(0, message.indexOf("\\"));
        }
        return ErrorMessages.DATABASE_ERROR_OCCURED.getMessage();
    }
}
