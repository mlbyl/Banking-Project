package com.mlbyl.bankingproject.exception;

public class InternalServerErrorException extends BaseException {
    public InternalServerErrorException(String message, String errorCode) {
        super(message, errorCode, 500);
    }

}
