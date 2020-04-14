package com.chz.component.crud_web;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//所有的请求都会被拦截,包括请求转发重定向和静态资源的访问
public class MyInterceptor implements HandlerInterceptor {
    @Override//请求之前处理
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登入成功后有用户的session
        if (request.getSession().getAttribute("loginUser") == null) {
            request.setAttribute("msg", "没有权限");
//            path时url
            request.getRequestDispatcher("/index").forward(request, response);
            return false;
        } else {
            return true;
        }
    }
}
