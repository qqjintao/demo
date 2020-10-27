package com.intertek.demo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 面向切面编程
 * @author jacksy.qin
 * @date 2019/9/23 13:27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerEndPoint {

    String operation() default "";
    String exceptionMessage() default "Benchmarking系统内部异常";
}
