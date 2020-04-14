package com.chz.conf;

import com.chz.conf.component.CustomizeRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConf {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/", "anon");
        filterMap.put("/index", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/**", "authc");
        //如果通过注解形式配置权限,setUnauthorizedUrl不会生效
        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuthc");
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setSecurityManager(manager);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager webSecurityManager(CustomizeRealm customizeRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customizeRealm);
        return securityManager;
    }

    @Bean
    public CustomizeRealm customizeRealm(HashedCredentialsMatcher matcher) {
        CustomizeRealm customizeRealm = new CustomizeRealm();
        //告诉Realm使用MD5加密
        customizeRealm.setCredentialsMatcher(matcher);
        return customizeRealm;
    }

    /**
     * 开启注解必须添加如下两个bean
     * 配置shiro与spring关联
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor advisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator creator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 加密
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        matcher.setHashAlgorithmName("MD5");
        //加密次数
        matcher.setHashIterations(10);
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }
}
