package com.chz.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
@Controller
public class MyController {

    @GetMapping("/test")
    public String test2() {
        return "user/test";
    }

    @GetMapping("/add")
    public String add() {
        return "/user/add";
    }

    @GetMapping("/update")
    public String update() {
        return "/user/update";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        return "/user/login";
    }

    /*
    登入逻辑处理
     */
    @RequestMapping("/login")
    public String login(String username, String password, Model model) {
        /*
        使用Shiro编写认证
         */
        //1.获取当前用户
        Subject currentUser = SecurityUtils.getSubject();
        System.out.println(currentUser.getPrincipal());
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //3.执行登入方法
            //拦截下来后转到UserRealm中的doGetAuthenticationInfo(AuthenticationToken token)
            currentUser.login(token);
            //登入成功
            return "user/success";
        } catch (UnknownAccountException e) {//用户名不存在
            model.addAttribute("msg","用户名不存在");
            //返回登入页面
            return "user/login";
        }catch (IncorrectCredentialsException e){//密码错误
            model.addAttribute("msg","密码错误");
            return "redirect:/login";
        }
    }
}
