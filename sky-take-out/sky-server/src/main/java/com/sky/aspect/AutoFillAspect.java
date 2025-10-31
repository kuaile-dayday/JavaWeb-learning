package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/*
* 自定义切面，实现公共字段自动填充处理逻辑
* */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /*
    * 切入点： 被指定的符合条件的方法（需要去执行什么操作）
    * */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {}

    /*
    * 前置通知，在通知中进行公共字段的赋值
    * （把符合条件的方法要执行的操作逻辑写进通知里面）
    * */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        // 连接点就是 我们写的一些方法，然后满足了规定的条件就是切入点
        log.info("开始进行公共字段自动填充...");

        // 不同的数据库操作类型，填充的字段也不同
        // 所以 1、获取数据库操作类型 insert赋值四个 update赋值两个
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();//  获取方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);// 获取方法上的注解对象
        // 获得数据库操作类型 （这里获得类型，应该是在mapper层 在对应方法上面添加的注解AutoFill括号中有value，对应update和insert）
        OperationType operationType = autoFill.value();


        // 2、获取当前被拦截的方法参数--实体对象
        // 这里会获取所有参数，比如 mapper层update方法：update(Employeee employee )
        // 则获取employee对象，args[0]就是employee对象；如果由多个参数，则args[0]为第一个参数，args[1]为第二个参数，以此类推
        // 一般 默认取第一个参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {// 判断空指针 即该方法没有参数
            return;
        }
        Object entity = args[0];// 不确定参数类型，所以用Object


        // 3、准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        // 4、根据对应的数据库操作类型，为对应的字段通过反射赋值
        if(operationType == OperationType.INSERT){
            // 为四个公共字段赋值
            // 如何获取 赋值方法 set
            try {
                // 获取该对象定义中的声明方法 getDeclaredMethod，参数1：方法名，参数2：参数类型
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 通过反射为对象属性赋值
                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (operationType == OperationType.UPDATE) {
            // 为两个公共字段赋值
            // 同理 先获取赋值方法
            try {
                // 获取该对象定义中的声明方法 getDeclaredMethod，参数1：方法名，参数2：参数类型
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 通过反射为对象属性赋值
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
