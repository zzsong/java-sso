package com.tm.client.interceptor;

import com.tm.client.sso.SsoClientManager;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Named
public class SsoClientInterceptor implements HandlerInterceptor {

    @Inject
    private SsoClientManager ssoClientManager;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //1、验证是否登录
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        if (Boolean.TRUE.equals(isLogin)){
            return true;
        }

        // 验证token登录过
        String token =  request.getParameter("token");
        if (StringUtils.isEmpty(token)){
            //没有会话，跳转到认证中心。检测是否登录
            ssoClientManager.sendSsoCheck(request, response);
        } else {
            //有会话，登录到认证服务中心进行验证
            String account = ssoClientManager.sendSsoVerify(token);
            if (StringUtils.isEmpty(account)){
                //验证失败，重新跳转到登录页面
                ssoClientManager.sendSsoLogin(request, response);
            } else {
                session.setAttribute("isLogin",true);
                request.setAttribute("account",account);
                return true;
            }
        }
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 Exception ex) throws Exception {
    }
}
