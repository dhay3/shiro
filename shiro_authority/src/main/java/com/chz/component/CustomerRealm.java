package com.chz.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chz.dao.TeacherMapper;
import com.chz.entity.Teacher;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    TeacherMapper teacherMapper;

    /**
     * 执行授权逻辑
     *
     * @param principals 用户
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        /*getPrimaryPrincipal即获取
         SimpleAuthenticationInfo(teacher.getTName(), teacher.getPassword(), getName());,
         中传递的principal动态获取用户权限*/
        String username = principals.getPrimaryPrincipal().toString();
        Teacher teacher = teacherMapper.selectOne(
                new QueryWrapper<Teacher>().eq("t_name", username));
        //添加数据库中对应的权限
        info.addStringPermission(teacher.getPerms());
        return info;
    }

    /**
     * 执行认证逻辑
     *
     * @param token 令牌 controller中调用login(),将token传入到该函数
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        Teacher teacher = teacherMapper.selectOne(
                new QueryWrapper<Teacher>().eq("t_name", username));
        System.out.println(teacher);
        if (null == teacher) {
            throw new UnknownAccountException();
        } else if (!Objects.equals(password, teacher.getPassword())) {
            throw new IncorrectCredentialsException();
        }
        //principal,credential,realmName
        return new SimpleAuthenticationInfo(teacher.getTName(), teacher.getPassword(), getName());
    }
}
