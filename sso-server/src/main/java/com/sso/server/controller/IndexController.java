package com.sso.server.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView toLogin(String redirectUrl, ModelAndView modelAndView){
        modelAndView.addObject("redirectUrl",redirectUrl);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("login")
    public ModelAndView login(String account, String password, String redirectUrl, HttpSession session, ModelAndView modelAndView){
        if ("admin".equals(account) && "123456".equals(password)){
            String token = UUID.randomUUID().toString();
            System.out.println("login success, token=>"+token);
            loginCacheMap.put(token,account);

            session.setAttribute("token",token);
            modelAndView.setViewName("redirect:"+redirectUrl);
            return modelAndView;
        }
        modelAndView.addObject("redirectUrl",redirectUrl);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     * 检查登录
     * @param redirectUrl
     * @param session
     * @return
     */
    @RequestMapping("checkLogin")
    public ModelAndView checkLogin(String redirectUrl, HttpSession session, ModelAndView modelAndView){
        String token = (String) session.getAttribute("token");
        System.out.println("checkLogin==>"+redirectUrl+" \t token=>"+token);
        if (StringUtils.isEmpty(token)){
            //无会话，跳转到登录页面
            modelAndView.addObject("redirectUrl",redirectUrl);
            modelAndView.setViewName("login");
            return modelAndView;
        }

        //登录成功，返回请求Url 并带上token
        modelAndView.addObject("token",token);
        modelAndView.setViewName("redirect:"+redirectUrl);
        return modelAndView;
    }

    @RequestMapping("verify")
    public String verify(String token){
        return loginCacheMap.get(token);
    }
}
