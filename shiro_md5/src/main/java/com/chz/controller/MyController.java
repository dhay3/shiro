package com.chz.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyController {
    @RequiresRoles("admin")
    @GetMapping("/add")
    public String add() {
        return "list";
    }

    @RequiresRoles("admin")//只有指定的角色才有权限访问该uri
    @GetMapping("/delete")
    public String delete() {
        return "list";
    }

    @GetMapping("/update")
    public String update() {
        return "list";
    }

    @RequiresPermissions("add")//只有指定拥有权限才能访问该uri
    @GetMapping("/select")
    public String select() {
        return "list";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout(){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "/login";
    }
    @PostMapping("/login")
    public String login(@RequestParam(required = false) String username,
                        @RequestParam(required = false) String password, Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
                /*调用login实际是securityManager的login(),然后调用authenticate()
                如果是一个用户就会调用doSingleRealmAuthentication(),然后
                调用realm的doGetAuthenticationInfo(token),进行用户认证
                */
            currentUser.login(token);
            //记住我
//                token.setRememberMe(true);
            return "/index";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名错误");
            return "/login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "/login";
        }
    }

}
