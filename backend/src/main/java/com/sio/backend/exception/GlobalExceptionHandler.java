package com.sio.backend.exception;

import com.sio.backend.generics.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleAll(Exception ex){
        return ApiResponse.error(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "GENERIC_ERROR"
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(IllegalArgumentException ex){
        ApiResponse<Void> body = ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNotFound(EntityNotFoundException ex){
        return ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNoSuchElement(NoSuchElementException ex){
        return ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMethNotValid(MethodArgumentNotValidException ex){
        return ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getBindingResult().getFieldErrors().stream()
                        .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                        .reduce((s1, s2) -> s1 + "; " + s2)
                        .orElse("Validation error")
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMethNotValid(HttpMessageNotReadableException ex){
        return ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleTransaction(TransactionSystemException ex){
        return ApiResponse.fail(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }
}
