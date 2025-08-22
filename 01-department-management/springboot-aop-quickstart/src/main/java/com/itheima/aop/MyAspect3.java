package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MyAspect3 {
//    @Before("execution(public void com.itheima.service.impl.DeptServiceImpl.delete(java.lang.Integer))")
//    @Before("execution(* com.itheima.service.impl.DeptServiceImpl.list(..)) ||" +
//            "execution(* com.itheima.service.impl.DeptServiceImpl.delete(..))")
    @Before("@annotation(com.itheima.anno.LogOperation)")
    public void before() {
        log.info("MyAspect3.before()");
    }
}
