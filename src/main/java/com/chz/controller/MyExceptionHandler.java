package com.chz.controller;

import com.chz.component.crud_web.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 想要客户端拿到自定义的json,浏览器显示自定的错误页面
 */
@ControllerAdvice//接管全局的异常处理
public class MyExceptionHandler {
 //1  第一种浏览器和客户端都返回json对象
/*        @ExceptionHandler(MyException.class)
    //参数不能有model,modelAndView,map...
    @ResponseBody//浏览器和客户端返回都是json
    private Map<String, Object> handleException(Exception e){
        //捕获异常并对异常筛选信息然后返回到前端
        HashMap<String, Object> map = new HashMap<>();
        map.put("code","user.notExist");
        map.put("message",e.getMessage());
        return map;//返回到前端自定json对象
    }*/
//2  第二种无法携带定制的数据到客户端,需要自定义给ErrorAttributes
    @ExceptionHandler(MyException.class)
    private String handleException(Exception e, HttpServletRequest request) {
        //捕获异常并对异常筛选信息然后返回到前端
        HashMap<String, Object> map = new HashMap<>();//map同样能被forward转到前端
        //通过这种方法无法传参数到前端,应为BasicError调用的是DefaultErrorAttributes的getAttributes方法
        map.put("code", "user.notExist");
//        map.put("message", e.getCause());
        request.setAttribute("map",map);//将属性放入到自定义的getAttributes()中
        //设置错位状态码让BasicErrorController能解析到
        request.setAttribute("javax.servlet.error.status_code", 500);
/*
        BasicErrorController通过getStatus获取到请求中通过request.getAttribute("javax.servlet.error.status_code");设设置的状态码
        但是当前的请求转发是正常的200,BasicErrorController不能解析到resolveErrorView(错误视图)就跳转到new ModelAndView("error", model)
*/
        return "forward:/error";//对应BasicErrorController
    }
}
