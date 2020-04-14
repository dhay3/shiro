package com.chz.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";//跳转到login.html
    }

    @RequestMapping("/login")
    public String login(String username,
                        String password, Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //执行该方法通过SecurityManager代理Realm进行用户验证
            currentUser.login(token);//token会被绑定到Subject上
            //为什么redirect的页面没有被拦截,原因不清楚,开发时返回json配合setSuccessUrl()才是标准的
            return "index";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名不存在");
            return "/login";//将消息携带传到login.html
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "/login";
        }
    }
}
