package com.git.msgintercept.internet.bean;

/**
 * @author yunnex
 * @date 2017/11/22
 */

public class BaseResponse {

    public static final int CODE_SUCCESS = 0;

    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "BaseResponse{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }

}
