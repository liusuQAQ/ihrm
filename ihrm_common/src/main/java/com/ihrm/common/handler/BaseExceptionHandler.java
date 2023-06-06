package com.ihrm.common.handler;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 自定义的公共异常处理器
 * 1.声明异常处理器
 * 2.对异常进行统一处理
 * */

@ControllerAdvice

public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response,Exception e){
        if(e.getClass() == CommonException.class){
            CommonException ce = (CommonException) e;
            System.out.println(e);
            Result result = new Result(ce.getResultCode());
            return result;
        }else {
            System.out.println(e);
            Result result = new Result(ResultCode.SERVER_ERROR);
            return result;
        }


    }
}
