package com.company.commons;
//使用枚举，实现统一异常处理
public enum Result {
    RESULT_SUCCESS(200,"SUCCESS"),
    RESULT_ERROR(500,"ERROR"),

    USERNAME_IS_NOT_EXIST(103,"用户名不存在！"),
    PASSWORD_IS_WRONG(104,"密码错误"),
    USER_EXIST(105,"用户已存在"),
    EMAIL_EXIST(106,"E-MAIL已存在"),
    PARAMTYPE_ERROR(107,"参数类型错误！只能选择用户名或EMAIL"),
    REGISTER_FAILED(108,"注册失败"),
    CONFIG_READ_ERRO(109,"配置文件读取错误,请重新选择路径"),

    LOGIN_SUCCESS(201,"登录成功"),
    LOGOUT_SUCCESS(202,"注销成功"),
    CHECK_SUCCESS(203,"校验成功，用户名/EMAIL可用"),
    REGISTER_SUCCESS(204,"注册成功"),
    GET_USER_INFO_SUCCESS(205,"获取用户信息成功"),
    GET_USER_INFO_ERROR(206,"用户尚未登录，获取用户信息失败"),
    GET_QUESTION_SUCCESS(207,"获取设置找回密码问题成功"),
    GET_QUESTION_ERROR(208,"该用户没有设置找回密码的问题"),
    ;

    private final int Status;
    private final String msg;

    Result(int status, String msg) {
        Status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return Status;
    }

    public String getMsg() {
        return msg;
    }
}
