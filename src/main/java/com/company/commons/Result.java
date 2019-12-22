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
    GET_TOKEN_SUCCESS(209,"获取token成功"),
    GET_TOKEN_ERROR(210,"预设问题回答错误，您无权修改密码"),
    TOKEN_GET_ERROR(211,"参数错误，需要传递token令牌"),
    TOKEN_TIMEOUT_ERROR(212,"TOKEN无效或者过期"),
    UPDATE_PASSWORD_SUCCESS(213,"密码修改成功"),
    UPDATE_PASSWORD_ERROR(214,"密码修改失败"),
    TOKEN_ISNOT_EXIST(215,"Token无效，请重新获取"),
    PASSWORD_ISPUT_ERROR(216,"旧密码错误，重新输入"),
    PASSWORD_UPDATE_SUCCESS(217,"更新密码成功"),
    PASSWORD_UPDATE_ERROR(218,"更新密码失败"),
    EMAIL_IS_ERROR(219,"EMAIL已被其他用户使用，请重新修改邮箱地址"),
    UPDATE_USER_INFO_SUCCESS(220,"更新个人信息成功"),
    UPDATE_USER_INFO_ERROR(221,"更新个人信息失败"),
    NOT_FOUND_USER_ERROR(222,"找不到当前用户"),
    USER_IS_NOT_MANAGER_ROLE(223,"该用户没有管理权限，无法登录后台管理系统"),
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
