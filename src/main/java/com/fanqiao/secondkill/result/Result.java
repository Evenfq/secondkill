package com.fanqiao.secondkill.result;

import lombok.Data;
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

    public Result(T data) {
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(SUCCESS.getCode(), SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> error(CodeMessage codeMessage) {
        return new Result<T>(codeMessage.getCode(), codeMessage.getMessage(), null);
    }

    public static <T> Result<T> message(CodeMessage codeMessage) {
        return new Result<T>(codeMessage.getCode(), codeMessage.getMessage(), null);
    }

    public static <T> Result<T>  fillArgs(CodeMessage codeMessage, Object... args) {
        String code = codeMessage.getCode();
        String message = String.format(codeMessage.getMessage(), args);
        return new Result<T>(code, message, null);
    }
}
