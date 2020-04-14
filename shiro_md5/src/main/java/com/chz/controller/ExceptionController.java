package com.chz.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {
    //通过注解形式设置role和perms,setUnauthorizedUrl不生效,要配置全局异常捕捉
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public String error() {
        return "你没有权限";
    }
}
