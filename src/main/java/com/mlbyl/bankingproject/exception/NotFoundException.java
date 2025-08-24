package com.mlbyl.bankingproject.exception;

public class NotFoundException extends BaseException {
    public NotFoundException(String message, String errorCode) {
        super(message, (errorCode != null ? errorCode + "_" : "") + "NOT_FOUND", 404);
    }
}
