package com.hqyj.javaSpringBoot.aspect;

import java.lang.annotation.*;

/**
 * author  Jayoung
 * createDate  2020/8/17 0017 14:32
 * version 1.0
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceAnnotation {
    String value() default  "aaa";
}
