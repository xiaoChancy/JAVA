package com.jyhd.black.enums;

public enum ResultEnum {

    LOGIN_SUCCESS(0,"登录成功"),
    LOGIN_USER(1,"账号不存在"),
    LOGIN_PASSWORD(2,"密码错误"),


    LOGIN_SIGN_ERROR(1001,"登录签名失败"),
    GAME_SIGN_ERROR(1002,"胜局签名失败"),
    THE_AWARD_TIME(1003,"领奖期间，不可积分"),
    GAME_REPEAT(1004,"不允许重复提交"),
    GAME_NAME_REPEAT(1005,"游戏名错误"),
    NOT_ON_THE_LIST(1006,"未上榜"),

    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
