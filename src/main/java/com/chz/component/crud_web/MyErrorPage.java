package com.chz.component.crud_web;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;

//通过这种方式的配置会让springboot的默认错误页面失效,不推荐
//@Component
public class MyErrorPage implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        //这里的path对应的是url地址
        registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/error404"));
        //如果页面出错就方法/hello url
        registry.addErrorPages(new ErrorPage("/hello"));
    }
}
