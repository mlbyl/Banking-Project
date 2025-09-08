package com.mlbyl.bankingproject.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends BaseException {
    public AccessDeniedException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.FORBIDDEN.value());
    }
}
