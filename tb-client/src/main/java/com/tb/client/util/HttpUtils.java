package com.tb.client.util;

import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpUtils {

    public static String sendGetHttpUrl(String requestUrl, Map<String,String> paramMap) throws Exception {
        //1、定义URL地址
        URL url = new URL(requestUrl);
        //2、连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //3、请求方式
        connection.setRequestMethod("GET");
        //4、准备参数
        if (paramMap != null && paramMap.size() >= 1)
        {
            StringBuffer sb = new StringBuffer();
            paramMap.forEach((k,v)->sb.append("&").append(k).append("=").append(v));
            connection.setDoOutput(true);
            connection.getOutputStream().write(sb.substring(1).getBytes(Charset.forName("UTF-8")));
        }
        //发起请求
        connection.connect();

        return StreamUtils.copyToString(connection.getInputStream(), Charset.forName("UTF-8"));
    }

}
