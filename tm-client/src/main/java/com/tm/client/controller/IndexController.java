package com.tm.client.controller;

import com.tm.client.config.SsoProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {

    @Inject
    private SsoProperties ssoProperties;

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @RequestMapping("member")
    public ModelAndView member(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("member");
        modelAndView.addObject("account",request.getAttribute("account"));
        return modelAndView;
    }
}
