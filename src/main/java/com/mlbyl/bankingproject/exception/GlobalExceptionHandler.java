package com.mlbyl.bankingproject.exception;

import com.mlbyl.bankingproject.utilities.results.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result<?>> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Result.failure(exception));
    }

    public ResponseEntity<Result<?>> handleBusinessException(BusinessException exception) {
        return ResponseEntity.badRequest()
                .body(Result.failure(exception));
    }
}
