package com.tb.client.config;

import com.tb.client.interceptor.SsoClientInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.inject.Inject;

@Configuration
@EnableWebMvc
public class TbClientWebConfig implements WebMvcConfigurer {

    @Bean
    @ConfigurationProperties(prefix = "sso")
    public SsoProperties ssoInstance(){
        return new SsoProperties();
    }

    @Inject
    private SsoClientInterceptor ssoClientInterceptor;

    @Override
    public  void addInterceptors(InterceptorRegistry registry) {
        //过滤首页/,  拦截所有其它请求
        registry.addInterceptor(ssoClientInterceptor).addPathPatterns("/**").excludePathPatterns("/");
    }

}
