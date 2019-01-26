package com.fanqiao.secondkill.result;


public enum CodeMessage {

    //通用
    SUCCESS("500000", "请求成功"),
    ERROR("500999", "请求失败")
    ;
    private String code;
    private String message;

    private CodeMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
