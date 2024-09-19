package com.jok.zxserver.domain;

/**
 * @Author JOKER
 * create time 2024/8/3 18:56
 */
public class R<T> {
    private int code;    // 状态码
    private String msg;  // 消息描述
    private T data;     // 响应数据

    // 构造函数
    public R() {
    }

    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 响应成功
    public static <T> R<T> ok(T data) {
        return new R<T>(200, "成功", data);
    }

    // 响应失败
    public static <T> R<T> fail(int code, String msg) {
        return new R<T>(code, msg, null);
    }
}