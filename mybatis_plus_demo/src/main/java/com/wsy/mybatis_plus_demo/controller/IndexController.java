package com.wsy.mybatis_plus_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/home")
    public String toHome(){
        return "home";
    }
    @GetMapping("/tologin")
    public String toLogin(){
        return "login";
    }
}
