package com.teng.exception;

import com.teng.enums.AppHttpCodeEnum;
import lombok.Data;

/**
 * @Author:xiaowang
 * @Data:2022/09/25/19:23 Description:
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