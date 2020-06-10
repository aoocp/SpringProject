package com.example.demo.util;

public class JsonResult <T>{

    /**
     * 表示成功状态
     */
    public static final int SUCCESS = 0;
    /**
     * 表示出错状态标志
     */
    public static final int ERROR = 1;

    /**
     * 状态
     */
    private int state;
    /**
     * 错误消息
     */
    private String message;
    /**
     * 返回正确的时候的数据
     */
    private T data;

    public JsonResult() {
    }

    public JsonResult(String message) {
        state = ERROR;
        this.message = message;
    }

    public JsonResult(T data) {
        state = SUCCESS;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }


    public void setState(Integer state) {
        this.state = state;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }
}
