package com.chz.controller;

import com.chz.component.crud_web.MyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello(@RequestParam("user") String user){
        if ("aaa".equals(user)){
            throw new MyException();
        }
        return "hello";
    }
}
