package com.chz.conf;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.chz.component.CustomerRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConf {
    /*
    Shiro 过滤url，交给SecurityManager代理
    主要配置一些拦截的url,放行的url..
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //必须使用LinkedHashMap否则会出现资源只能加载一次然后就被拦截的情况
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        /*
        添加shiro内置过滤器,可以实现权限相关的拦截器,同样会拦截静态资源
        常用过滤器:
        1. anon: 无需认证(登入)可以访问资源  anonymous
        2. authc: 必须认证才能访问   authority
        3. user: 如果使用rememberMe的功能才可以访问
        4. perms: 该资源必须得到资源权限才能访问
        5. role: 该资源必须得到角色权限才能访问
         */
        //anon的请求必须放在authc之上
        filterMap.put("/","anon");
        filterMap.put("/index","anon");
        filterMap.put("/hello","anon");
        filterMap.put("/login","anon");//放行login请求
        //授权过滤器,[]标识授权的字符串;如果访问指定页面,就会进入授权逻辑判断,如果没有权限就会跳转到为授权的页面
        filterMap.put("/add.html","perms[user:add]");
        filterMap.put("/update.html","perms[user:update]");
        filterMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        //set的这些url都会被shiro自动放行
        //如果当前site没有cookie就会跳转到该页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //shiroFilterFactoryBean.setSuccessUrl("/toLogin");
        //设置未授权的提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/uno.html");
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        return shiroFilterFactoryBean;
    }
    /*
     SecurityManager: 关联Realm，进行数据校验
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(CustomerRealm customerRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customerRealm);
        return defaultWebSecurityManager;
    }
    /*
    Realm： Shiro 连接数据的桥梁 用于数据验证
     */
    @Bean
    public CustomerRealm realm(){
        return new CustomerRealm();
    }

    /*
    配置ShrioDialect,用于整合thymeleaf和shiro标签,需要引入thymeleaf-extras-shiro
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
