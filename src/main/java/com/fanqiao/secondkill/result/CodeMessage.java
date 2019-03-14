package com.fanqiao.secondkill.result;


public enum CodeMessage {

    //通用
    SUCCESS("500000", "请求成功"),
    SERVER_ERROR("500777", "服务端异常"),
    ERROR("500999", "请求失败"),

    //登录模块
    PARAM_EMPTY_ERROR("6000001", "参数不能为空"),
    MOBILE_NUMBER_ERROR("6000002", "手机号格式不正确"),
    LOGIN_ERROR("6000002", "账号或密码错误"),
    BIND_ERROR("6000003", "参数校验异常：%s"),
    SECOND_KILL_USER_NOT_EXIST("6000004", "用户不存在"),

    //秒杀模块
    REPERTORY_EMPTY("7000001", "没有库存"),
    SECONDKILL_REPEAT("7000002", "重复秒杀"),

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
