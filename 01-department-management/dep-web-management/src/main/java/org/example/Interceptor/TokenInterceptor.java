package org.example.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/*
* 令牌校验的拦截器
* */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、获取请求路径
        String requestURI = request.getRequestURI();///emp

        // 2、判断是否是登录请求，如果路径中包含 /login 说明是登录操作 则放行
        if (requestURI.contains("/login")) {
            log.info("登录请求，放行");
            return true;
        }

        // 3、获取请求头中的token
        String token = request.getHeader("token");

        // 4、判断token存在，如果不存在说明用户没有登录，返回错误信息401
        if (token == null || token.isEmpty()) {
            log.info("令牌为空，响应401");
            response.setStatus(401);
            return false;
        }
        // 5、如果token存在，校验令牌，如果校验失败，返回错误信息401
        try {
            JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.info("令牌非法，响应401");
            response.setStatus(401);
            return false;
        }
        // 6、校验通过，放行
        log.info("令牌合法，放行");
        return true;
    }
}
