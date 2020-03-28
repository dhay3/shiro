package com.chz.conf;

import com.chz.component.crud_web.MyInterceptor;
import com.chz.component.crud_web.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConf implements WebMvcConfigurer {
    @Override//通过配置类重新引导首页,推荐(2.2.5会自动处理templates下的index.html)
    public void addViewControllers(ViewControllerRegistry registry) {
        //重定向的方式
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        //让handler的请求重定向到main.html再通过viewController跳转到dashboard为了安全
        registry.addViewController("/main").setViewName("dashboard");
    }

    @Override//添加拦截器,同样会拦截viewController中的请求,但是不会拦截错误跳转页面
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns想要拦截的请求路径,如不添加默认拦截所有
        //excludePathPatterns除这些请求外
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**")
                //放行静态资源的访问和首页的访问(直接访问main会被拦截)
                .excludePathPatterns("/login", "/asserts/**", "/index", "/");
    }

    @Bean//方法名必须是localeResolver因为@Bean的name是方法名
    //注入自己实现LocaleResolver接口的类WebMvcAutoConfiguration
    // 不再默认注入public LocaleResolver localeResolver()因为@ConditionalOnMissingBean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();//springboot某个功能组件会自动执行该类中resolveLocale()方法
    }
}
