package com.mlbyl.bankingproject.utilities.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mlbyl.bankingproject.exception.BaseException;
import lombok.Data;

@Data
public class Result<T> {
    private boolean success;
    private T data;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer statusCode;

    public Result(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public Result(boolean success, T data, String message, String errorCode, int statusCode) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(true, data, message);
    }

    public static Result<?> success(String message) {
        return new Result<>(true, null, message);
    }

    public static Result<?> failure(BaseException ex) {
        return new Result<>(false, null, ex.getMessage(), ex.getErrorCode(), ex.getStatusCode());
    }

    public static <T> Result<T> failure(T data, BaseException ex) {
        return new Result<>(false, data, ex.getMessage(), ex.getErrorCode(), ex.getStatusCode());
    }

}
