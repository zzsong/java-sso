package com.tb.client.controller;

import com.tb.client.config.SsoProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

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
    public ModelAndView member(String account){
        ModelAndView modelAndView = new ModelAndView("member");
        modelAndView.addObject("account","admin.....");
        return modelAndView;
    }
}
