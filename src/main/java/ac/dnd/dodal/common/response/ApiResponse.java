package ac.dnd.dodal.common.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;

import ac.dnd.dodal.common.enums.ResultCode;
import ac.dnd.dodal.common.enums.CommonResultCode;

public record ApiResponse<T>(

    String code,
    String message,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data
) {

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(
            CommonResultCode.SUCCESS.getCode(),
            CommonResultCode.SUCCESS.getMessage(),
            null
        );
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
            CommonResultCode.SUCCESS.getCode(),
            CommonResultCode.SUCCESS.getMessage(),
            data
        );
    }

    public static <T> ApiResponse<List<T>> success(List<T> data) {
        return new ApiResponse<>(
            CommonResultCode.SUCCESS.getCode(),
            CommonResultCode.SUCCESS.getMessage(),
            data
        );
    }

    public static <T> ApiResponse<Page<T>> success(Page<T> data) {
        return new ApiResponse<>(
            CommonResultCode.SUCCESS.getCode(),
            CommonResultCode.SUCCESS.getMessage(),
            data
        );
    }

    public static <T> ApiResponse<T> failure(ResultCode resultCode) {
        return new ApiResponse<>(
            resultCode.getCode(),
            resultCode.getMessage(),
            null
        );
    }

    public static <T> ApiResponse<T> failure(ResultCode resultCode, String message) {
        return new ApiResponse<>(
            resultCode.getCode(),
            message,
            null
        );
    }

    public static <T> ApiResponse<T> success(ResultCode resultCode) {
        return new ApiResponse<>(
            resultCode.getCode(),
            resultCode.getMessage(),
            null
        );
    }

    public static <T> ApiResponse<T> success(ResultCode resultCode, String message) {
        return new ApiResponse<>(
            resultCode.getCode(),
            message,
            null
        );
    }
}