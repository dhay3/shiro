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
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 用户认证后执行权限认证
 */
public class CustomizeRealm extends AuthorizingRealm {
    @Autowired
    UserMapper userMapper;

    /**
     * 权限认证
     *
     * @param principals 即用户
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new UnknownAccountException();
        }
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

    /**
     * 用户认证
     *
     * @param token token就是controller中设置的controller
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1. 将AuthenticationToken 转为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //2. 从UsernamepasswordToken中获取username,表单输入的username
        String username = usernamePasswordToken.getUsername();
        //3. 调用数据库方法,从数据库中查询出username对应的记录
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("name", username));
        //4. 用户不存在,则抛出异常UnknownAccountException
        // 有可能查询到的结果为null,需要判断一次
        if (null == user) {
            throw new UnknownAccountException();
        }
        //5. 根据用户信息,决定是否抛出其他的AuthenticationException异常
        if (Objects.equals(user.getName(), "老王")) {
            throw new LockedAccountException();
        }
        //6. 根据用户情况,来构建AuthenticationInfo对象,并返回
        //以下信息是从数据库中获取的,除第三点外
        //1). principal: 认证的实体信息,可以是usename,也可以是数据表对应的实体类对象
        String principal = user.getName();
        //2). credentials: 密码
        String credentials = user.getPassword();
        //3). 计算对应用户名的盐值,一般使用随机字符串或user id
        ByteSource salt = ByteSource.Util.bytes(username);
        //4). realmName: 当前realm对象的name,调用父类的getName()方法即可
        String realmName = getName();
        //7. 密码校验由shiro校验,抛到controller中
        //带上盐值比对数据库密码
        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(principal, credentials, salt, realmName);
        return simpleAuthenticationInfo;
    }
}
