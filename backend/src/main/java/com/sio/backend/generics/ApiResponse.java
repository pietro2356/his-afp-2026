package com.sio.backend.generics;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private ApiResponseStatus status;
    private Integer statusCode;
    private Instant timestamp;

    private T data;

    private String message;
    private String errorCode;
    private String stackTrace;

    // --- SUCCESS ---
    public static <T> ApiResponse<T> success(T data){
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .data(data)
                .build();
    }
    public static <T extends Collection<?>> ApiResponse<T> success(T data){
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .data(data)
                .build();
    }

    // -- ERROR --
    public static <T> ApiResponse<T> error(String message, Integer statusCode){
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.ERROR)
                .statusCode(statusCode)
                .timestamp(Instant.now())
                .message(message)
                .build();
    }
    public static <T> ApiResponse<T> error(String message, Integer statusCode, String errorCode){
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.ERROR)
                .statusCode(statusCode)
                .timestamp(Instant.now())
                .message(message)
                .errorCode(errorCode)
                .build();
    }

    // --- FAIL ---
    public static <T> ApiResponse<T> fail(String message, Integer statusCode){
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.FAIL)
                .statusCode(statusCode)
                .timestamp(Instant.now())
                .message(message)
                .build();
    }
    public static <T> ApiResponse<T> fail(String message, Integer statusCode, String errorCode){
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.FAIL)
                .statusCode(statusCode)
                .timestamp(Instant.now())
                .message(message)
                .errorCode(errorCode)
                .build();
    }
}

/**
 * status - SUCCESS | FAIL | ERROR
 * code
 * timestamp
 *
 * data
 *
 * message
 * code
 * stacktrace
 */
