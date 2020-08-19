package com.hqyj.javaSpringBoot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * author  Jayoung
 * createDate  2020/8/17 0017 13:54
 * version 1.0
 */
@Aspect
@Component
public class ControllerAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    /**
     * 关联在方法上的切点
     * 第一个*代表返回类型不限
     * 第二个*代表module下所有子包
     * 第三个*代表所有类
     * 第四个*代表所有方法
     * (..) 代表参数不限
     * Order 代表优先级，数字越小优先级越高
     */
    @Pointcut("execution(public * com.hqyj.javaSpringBoot.modules.*.controller.*.*(..))")
    @Order(1)
    public void controllerPointcut(){}

    @Before(value = "com.hqyj.javaSpringBoot.aspect.ControllerAspect.controllerPointcut()")
    public void beforeController(JoinPoint joinPoint){
        LOGGER.debug("==== This is before controller =====");
        //获得request对象的一种方法
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        LOGGER.debug("请求来源："+request.getRemoteAddr());
        LOGGER.debug("请求URL："+request.getRequestURL().toString());
        LOGGER.debug("请求方式："+request.getMethod());
        LOGGER.debug("响应方式："+joinPoint.getSignature().getDeclaringTypeName()+"."
        +joinPoint.getSignature().getName());
        LOGGER.debug("请求参数："+ Arrays.toString(joinPoint.getArgs()));

    }

    @Around(value = "com.hqyj.javaSpringBoot.aspect.ControllerAspect.controllerPointcut()")
    //注意around方法 返回类型为Object，参数类型ProceedingJoinPoint
    public Object arroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LOGGER.debug("==== This is around controller ==== ");
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }

    @After(value = "com.hqyj.javaSpringBoot.aspect.ControllerAspect.controllerPointcut()")
    public void afterController(){
        LOGGER.debug("==== This is after controller ====");
    }
}
