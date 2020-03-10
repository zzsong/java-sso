package com.sso.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class IndexController {

    private Map<String, String> loginCacheMap = new ConcurrentHashMap<>();

    @RequestMapping(value = {"/","index"})
    public String index(String appId){
        return "hello:"+appId;
    }

    @RequestMapping("toLogin")
    public String toLogin(String redirectUrl, Model model){
        model.addAttribute("redirectUrl",redirectUrl);
        return "login";
    }

    @RequestMapping("login")
    public String login(String account, String password, String redirectUrl, HttpSession session, Model model){
//        RedirectView redirectView = new RedirectView("");
        System.out.println(account+"====>"+password+"--->"+redirectUrl);
        if ("admin".equals(account) && "123456".equals(password)){
            String token = UUID.randomUUID().toString();
            System.out.println("login success, token=>"+token);
            loginCacheMap.put(token,account);
            model.addAttribute("token",token);
            return "redirect:"+redirectUrl;
        }
        model.addAttribute("redirectUrl",redirectUrl);
        return "login";
    }

    /**
     * 检查登录
     * @param redirectUrl
     * @param session
     * @return
     */
    @RequestMapping("checkLogin")
    public String checkLogin(String redirectUrl, HttpSession session, Model model){
        String token = (String) session.getAttribute("token");
        System.out.println("checkLogin==>"+redirectUrl+" \t token=>"+token);
        if (StringUtils.isEmpty(token)){
            //无会话，跳转到登录页面
//            modelAndView.addObject("redirectUrl",redirectUrl);
            model.addAttribute("redirectUrl",redirectUrl);
            return "login";
        }

        //登录成功，返回请求Url 并带上token
        model.addAttribute("token",token);
        return "redirect:"+redirectUrl;
    }

    @RequestMapping("verify")
    @ResponseBody
    public String verify(String token){
        return loginCacheMap.get(token);
    }
}
