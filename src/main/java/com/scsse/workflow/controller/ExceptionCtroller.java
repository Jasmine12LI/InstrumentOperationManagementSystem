package com.scsse.workflow.controller;

import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultCode;
import com.scsse.workflow.util.result.ResultUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.BindException;

@RestControllerAdvice
public class ExceptionCtroller {

    @ExceptionHandler(value = AuthorizationException.class)
    public Result defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) throws Exception{
        return ResultUtil.error(ResultCode.CODE_403);
    }
    @ExceptionHandler(value =Exception.class)
    public Result ExceptionHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) throws Exception{
        return ResultUtil.error(ResultCode.CODE_507);
    }

}
