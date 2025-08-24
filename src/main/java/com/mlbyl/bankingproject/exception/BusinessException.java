package com.mlbyl.bankingproject.exception;

public class BusinessException extends BaseException {
    public BusinessException(String message, String errorCode, Integer statusCode) {
        super(message, errorCode, statusCode);
    }

}
