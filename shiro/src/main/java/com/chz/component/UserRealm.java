package com.chz.component;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Objects;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {
    /*
    执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权逻辑");
        return null;
    }
    /*
    执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        String name= "chz";
        String password="123";
        //token就是controller中的token
        UsernamePasswordToken principal = (UsernamePasswordToken) token;
        if (!Objects.equals(name,principal.getUsername())){
            //用户名不存在
            return null;//返回null,shiro底层抛出UnKnowAccountException
        }
        //判断密码,第一个参数是需要返回给/login的数据,第二个参数是认证即密码
        return new SimpleAuthenticationInfo("",password,"");
    }
}
