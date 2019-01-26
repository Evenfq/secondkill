package com.fanqiao.secondkill.result;

import lombok.Data;

import static com.fanqiao.secondkill.result.CodeMessage.ERROR;
import static com.fanqiao.secondkill.result.CodeMessage.SUCCESS;

@Data
public class Result<T> {
    private String code;
    private String message;
    private T data;

    private Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(SUCCESS.getCode(), SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> error(CodeMessage codeMessage) {
        return new Result<T>(codeMessage.getCode(), codeMessage.getMessage(), null);
    }
}
