package org.example.config;

import org.example.Interceptor.DemoInterceptor;
import org.example.Interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
* 配置类
* */
@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Autowired
//    private DemoInterceptor demoInterceptor;

//    @Autowired
//    private TokenInterceptor tokenInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");//拦截所有请求
//    }
}
