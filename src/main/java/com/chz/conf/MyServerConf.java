package com.chz.conf;

import com.chz.component.filter.MyFilter;
import com.chz.component.listener.MyListener;
import com.chz.component.servlet.MyServlet;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

//
@Configuration
public class MyServerConf {
    //配置嵌入式的servlet容器
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>() {
            //定制嵌入式的servlet容器相关规则
            @Override
            public void customize(TomcatServletWebServerFactory factory) {
                factory.setPort(8085);
            }
        };
    }
    //注册servlet组件(不推荐这种方法,推荐用注解形式注入)
    @Bean
    public ServletRegistrationBean<MyServlet> myServlet() {
        ServletRegistrationBean<MyServlet> servlet = new ServletRegistrationBean<>
                (new MyServlet(), "/servlet");
        servlet.setLoadOnStartup(1);
        return servlet;
    }

    @Bean
    public FilterRegistrationBean<MyFilter> myFilter() {
        FilterRegistrationBean<MyFilter> filter = new FilterRegistrationBean<>(new MyFilter());
//        filter.setFilter(new MyFilter());
        filter.setUrlPatterns(Arrays.asList("/hello", "/servlet"));
        return filter;
    }

    @Bean
    public ServletListenerRegistrationBean<MyListener> myListener() {
        ServletListenerRegistrationBean<MyListener> listener = new ServletListenerRegistrationBean<>(new MyListener());
//        listener.setListener(new MyListener());
        return listener;
    }

}
