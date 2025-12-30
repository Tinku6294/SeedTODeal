package com.Coder.UserService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private int statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    // ------------------ Success Responses ------------------
    public static <T> ApiResponse<T> success(int statusCode, String message, T data) {
        return new ApiResponse<>(true, statusCode, message, data, null);
    }

    public static <T> ApiResponse<T> success(int statusCode, String message) {
        return new ApiResponse<>(true, statusCode, message, null, null);
    }

    // ------------------ Error Responses ------------------
    public static <T> ApiResponse<T> error(int statusCode, String errorMessage) {
        return new ApiResponse<>(false, statusCode, null, null, errorMessage);
    }

    public static <T> ApiResponse<T> error(int statusCode, String errorMessage, T data) {
        return new ApiResponse<>(false, statusCode, null, data, errorMessage);
    }
}
