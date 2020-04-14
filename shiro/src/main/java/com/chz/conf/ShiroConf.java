package com.chz.conf;

//import com.chz.component.UserRealm;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import com.chz.component.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * shiro 配置类
 * Subject： 用户主体（把操作交给SecurityManager）
 * SecurityManager：安全管理器（关联Realm）
 * Realm：Shiro连接数据的桥梁
 */
@Configuration
public class ShiroConf {
    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    //调用方法模式不太对，直接在Bean中给参数即可，@Bean参数自动@Autowire
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器SecurityManager
        filterFactoryBean.setSecurityManager(manager());
        /*
        添加shiro内置过滤器,可以实现权限相关的拦截器
        常用过滤器:
        1. anon: 无需认证(登入)可以访问资源  anonymous
        2. authc: 必须认证才能访问   authority
        3. user: 如果使用rememberMe的功能才可以访问
        4. perms: 该资源必须得到资源权限才能访问
        5. role: 该资源必须的得到角色权限才能访问
         */
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        //设置无需认证也能访问
        map.put("/test", "anon");
        //必须认证才能访问该uri
        map.put("/add", "authc");
        map.put("/update", "authc");
        map.put("/user/login", "anon");
        //同样会拦截返回的请求
        map.put("/user/**", "authc");
//        map.put("/**","authc");
        //修改默认无权限(未登入)跳转到login.jsp,修改到/toLogin,该请求处理的不会被拦截
        filterFactoryBean.setLoginUrl("/toLogin");
        filterFactoryBean.setFilterChainDefinitionMap(map);
        return filterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
//    @Bean
    public DefaultWebSecurityManager manager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //关联Real
        manager.setRealm(userRealm());
        return manager;
    }

    /**
     * 创建Realm
     */
//    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }
}
