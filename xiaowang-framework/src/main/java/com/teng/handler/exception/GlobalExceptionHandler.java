package com.teng.handler.exception;

import com.teng.domin.ResponseResult;
import com.teng.enums.AppHttpCodeEnum;
import com.teng.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

/**
 * @Author:xiaowang
 * @Data:2022/09/26/9:29 Description:
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(Exception e){
        //打印异常信息
        log.error("出现异常! {}",e);
        if (e instanceof SystemException){
            //从异常对象中获取提示信息  封装返回
            return ResponseResult.errorResult(((SystemException) e).getCode(),e.getMessage());
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
        }
    }


    //文件上传异常监控
    @ExceptionHandler(MultipartException.class)
    public ResponseResult fileSizeException(Exception e){
        log.error("出现异常! {}",e);
        return ResponseResult.errorResult(AppHttpCodeEnum.FILE_SIZE);
    }

}
