package com.yecheng.exception;

import com.yecheng.enums.AppHttpCodeEnum;

/**
 * 系统异常,自定义异常
 *
 * @author Yefl
 * @date 2022/10/13
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
    
}