package com.chz.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    //登录请求处理
    @RequestMapping(path = "/login")
    public String login(@RequestParam(required = false) String username,
                        @RequestParam(required = false) String password,
                        Model model,
                        HttpSession session) {//通过session(当前会话中都会有效)向拦截器中传值
        if (!StringUtils.isEmpty(username) && password.equals("123")) {
            //设置session,拦截器通过session判断
            session.setAttribute("loginUser", username);
            //重定向url,重定向不会带数据信息(request中的信息不会得到)
            return "redirect:main";//登录成功,请求转发到main再通过viewController
        } else {
            model.addAttribute("msg", "用户名或密码错误");
            return "index";//登录失败
        }
    }
}
