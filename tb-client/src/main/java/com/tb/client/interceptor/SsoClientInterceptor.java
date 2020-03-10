package com.tb.client.interceptor;

import com.tb.client.support.sso.SsoClientManager;
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
        String token = (String) session.getAttribute("token");
        if (StringUtils.isEmpty(token)){
            ssoClientManager.sendSsoLogin(request, response);
        } else {
            String account = ssoClientManager.sendSsoVerify(token);
            if (StringUtils.isEmpty(account)){
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
