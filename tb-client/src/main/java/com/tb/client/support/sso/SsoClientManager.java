package com.tb.client.support.sso;

import com.tb.client.config.SsoProperties;
import com.tb.client.util.HttpUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Named
public class SsoClientManager {

    @Inject
    private SsoProperties ssoProperties;

    public void sendSsoLogin(HttpServletRequest request,  HttpServletResponse response) throws IOException {
        String redirect = ssoProperties.getServerUrl()+"/toLogin?redirectUrl="+ssoProperties.getClientUrl()+request.getServletPath();
        System.out.println(redirect);
        response.sendRedirect(redirect);
    }

    public void sendSsoCheck(HttpServletRequest request,  HttpServletResponse response) throws IOException {
        String redirect = ssoProperties.getServerUrl()+"/checkLogin?redirectUrl="+ssoProperties.getClientUrl()+request.getServletPath();
        System.out.println(redirect);
        response.sendRedirect(redirect);
    }

    public String sendSsoVerify(String token) throws IOException {
        String requestUrl = ssoProperties.getServerUrl()+"/verify";
        Map<String, String> params = new HashMap<>();
        params.put("token",token);
        try {
            return HttpUtils.sendGetHttpUrl(requestUrl,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
