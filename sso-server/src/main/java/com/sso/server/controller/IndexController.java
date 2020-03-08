package com.sso.server.controller;

import com.sso.server.domain.UserInfo;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

    @RequestMapping(value = {"/","index"})
    public String index(String appId){
        return "hello:"+appId;
    }

    @RequestMapping("toLogin")
    public ModelAndView toLogin(String redirectUrl){
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("redirectUrl",redirectUrl);
        return modelAndView;
    }

    @RequestMapping("login")
    public String login(@ModelAttribute("user") UserInfo user){

        System.out.println(user.getAccount()+"====>"+user.getPassword()+"--->"+user.getRedirectUrl());
        if ("admin".equals(user.getAccount()) && "123456".equals(user.getPassword())){
            return "login success";
        }
        return "login fail";
    }
}
