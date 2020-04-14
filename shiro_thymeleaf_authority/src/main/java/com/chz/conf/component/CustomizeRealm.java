package com.chz.conf.component;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chz.dao.UserMapper;
import com.chz.entity.Perm;
import com.chz.entity.Role;
import com.chz.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class CustomizeRealm extends AuthorizingRealm {
    @Autowired
    UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String username = principals.getPrimaryPrincipal().toString();
        User users = userMapper.queryUserRoles(username);
        for (Role role : users.getRoles()) {
            //获取角色
            simpleAuthorizationInfo.addRole(role.getRole());
            for (Perm perm : role.getPerms()) {
                //获取角色权限
                simpleAuthorizationInfo.addStringPermission(perm.getPerm());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        String password = new String((char[]) token.getCredentials());
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("name", username));
         if (null==user||!Objects.equals(user.getName(), username)) {
            throw new UnknownAccountException();
        }
         //不需要判断密码交给SimpleAuthenticatinInfo判断
        return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());
    }
}
