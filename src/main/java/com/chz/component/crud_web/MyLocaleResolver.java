package com.chz.component.crud_web;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    @Override//所有的请求(包括请求转发,重定向)都会经过改解析器
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = Locale.getDefault();//如果没有就返回空
        String lan = request.getParameter("lan");//获取请求参数,第一请求经过解析器报空指针
       if (lan!=null){
           String[] s = lan.split("_");
           if (!StringUtils.isEmpty(lan)) {
               locale = new Locale(s[0], s[1]);
           }
       }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    }
}
