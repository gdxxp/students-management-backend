package com.students.studentsmanagement.utils;

import com.students.studentsmanagement.constant.ResponseCodes;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data
@Builder
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 2315862136552967965L;

    private Integer code;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success() {
        return BaseResponse.<T>builder()
                .code(ResponseCodes.SUCCESS.getCode())
                .message(ResponseCodes.SUCCESS.getMessage())
                .data(null)
                .build();
    }

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .code(ResponseCodes.SUCCESS.getCode())
                .message(ResponseCodes.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> success(String message) {
        return BaseResponse.<T>builder()
                .code(ResponseCodes.SUCCESS.getCode())
                .message(message)
                .data(null)
                .build();
    }

    public static <T> BaseResponse<T> success(T data, String message) {
        return BaseResponse.<T>builder()
                .code(ResponseCodes.SUCCESS.getCode())
                .data(data)
                .message(message)
                .build();
    }

    public static <T> BaseResponse<T> fail(ResponseCodes responseCodes) {
        return BaseResponse.<T>builder()
                .code(responseCodes.getCode())
                .message(responseCodes.getMessage())
                .data(null)
                .build();
    }

    public static <T> BaseResponse<T> fail(ResponseCodes responseCodes, String message) {
        return BaseResponse.<T>builder()
                .code(responseCodes.getCode())
                .message(message)
                .data(null)
                .build();
    }
}