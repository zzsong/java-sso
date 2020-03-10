package com.sso.server.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class IndexController {

    private Map<String, String> loginCacheMap = new ConcurrentHashMap<>();

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
    public ModelAndView login(String account, String password, String redirectUrl, HttpSession session){
        ModelAndView modelAndView = new ModelAndView("");
        System.out.println(account+"====>"+password+"--->"+redirectUrl);
        if ("admin".equals(account) && "123456".equals(password)){
            String token = UUID.randomUUID().toString();
            loginCacheMap.put(token,account);
            modelAndView.setViewName("forward:"+redirectUrl);
            modelAndView.addObject("token",token);
            RedirectView redirectView = new RedirectView();
//            redirectView.
            return modelAndView;
        }
        modelAndView.setViewName("login");
        modelAndView.addObject("redirectUrl",redirectUrl);
        return modelAndView;
    }

    /**
     * 检查登录
     * @param redirectUrl
     * @param session
     * @return
     */
    @RequestMapping("checkLogin")
    public ModelAndView checkLogin(String redirectUrl, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();

        String token = (String) session.getAttribute("token");

        if (StringUtils.isEmpty(token)){
            modelAndView.addObject("redirectUrl",redirectUrl);
            modelAndView.setViewName("login");
        } else {
            modelAndView.setViewName("redirect:"+redirectUrl);
            modelAndView.addObject("token",token);
        }
        return modelAndView;
    }

    @RequestMapping("verify")
    public String verify(String token){
        return loginCacheMap.get(token);
    }
}
