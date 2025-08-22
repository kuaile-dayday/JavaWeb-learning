package com.itheima.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标记需要记录操作日志的方法
 * 
 * @Target(ElementType.METHOD) 表示该注解只能用于方法上
 * @Retention(RetentionPolicy.RUNTIME) 表示该注解在运行时保留，可以通过反射获取
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogOperation {
}
