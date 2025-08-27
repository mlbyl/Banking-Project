package com.mlbyl.bankingproject.exception;

public class ValidationException extends BaseException {
    public ValidationException(String message, String errorCode) {
        super(message, errorCode, 400);
    }
}
