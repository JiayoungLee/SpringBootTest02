package com.hqyj.javaSpringBoot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * author  Jayoung
 * createDate  2020/8/17 0017 14:34
 * version 1.0
 */
@Aspect
@Component
public class ServiceAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    @Pointcut("@annotation(com.hqyj.javaSpringBoot.aspect.ServiceAnnotation)")
    @Order(2)
    public void servicePointCut(){}


    @Before(value = "com.hqyj.javaSpringBoot.aspect.ServiceAspect.servicePointCut()")
    public void beforeService(JoinPoint joinPoint){
        LOGGER.debug("==== This is before controller =====");


    }

    @Around(value = "com.hqyj.javaSpringBoot.aspect.ControllerAspect.controllerPointcut()")
    public Object arroundService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LOGGER.debug("==== This is around controller ==== ");
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }

    @After(value = "com.hqyj.javaSpringBoot.aspect.ControllerAspect.controllerPointcut()")
    public void afterService(){
        LOGGER.debug("==== This is after controller ====");
    }
}
