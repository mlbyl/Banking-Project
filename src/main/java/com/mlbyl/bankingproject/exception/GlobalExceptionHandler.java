package com.mlbyl.bankingproject.exception;

import com.mlbyl.bankingproject.utilities.constants.ErrorCodes;
import com.mlbyl.bankingproject.utilities.constants.ErrorMessages;
import com.mlbyl.bankingproject.utilities.results.Result;
import org.apache.coyote.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result<?>> handleNotFoundException(NotFoundException exception) {
        return createResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<?>> handleBusinessException(BusinessException exception) {
        return createResponse(exception, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Result<?>> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception) {

        BaseException ex = new DatabaseException(
                exception.getMostSpecificCause().getMessage(),
                ErrorCodes.DB.name(), 409);
        return createResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<?>> handleValidationExceptionn(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        BaseException ex = new ValidationException(ErrorMessages.VALIDATION_ERROR_OCCURED.getMessage(),
                ErrorCodes.VALIDATION.name());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Result.failure(errors, ex));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleRuntimeException(Exception exception) {
        BaseException ex = new InternalServerErrorException(exception.getMessage()
                , ErrorCodes.SERVER.name());
        return createResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<Result<?>> createResponse(BaseException exception, HttpStatus status) {
        return ResponseEntity.status(status).body(Result.failure(exception));
    }

}
