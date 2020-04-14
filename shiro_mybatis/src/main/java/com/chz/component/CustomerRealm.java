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

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    TeacherMapper teacherMapper;

    /**
     * 执行授权逻辑
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //所有的用户都授权
        System.out.println("授权逻辑");
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加资源的授权字符串
        info.addStringPermission("user:add");
        return info;
    }

    /**
     * 执行认证逻辑
     * @param token controller中调用login(),将token传入到该函数
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        UsernamePasswordToken user=(UsernamePasswordToken)token;
        String username = (String) token.getPrincipal();
        System.out.println("username====>"+username);
        //shiro底层密码用char[]字符串存储,密码转成String后要用String接受
        String password = new String((char[]) token.getCredentials());
        System.out.println("password====>"+password);
        //可以通过sql校验用户名和密码,也可以通过shiro判断密码
        Teacher teacher = teacherMapper.selectOne(
                new QueryWrapper<Teacher>().eq("t_name", username));
        System.out.println(teacher);
        if (null == teacher) {
//            return null;//返回null 自动交给捕捉的第一个异常处理
            // 注意数据类型
            throw  new UnknownAccountException();
        }else if (!Objects.equals(password,teacher.getPassword())){
//            return null;
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(teacher.getTName(), teacher.getPassword(), getName());
    }
}
