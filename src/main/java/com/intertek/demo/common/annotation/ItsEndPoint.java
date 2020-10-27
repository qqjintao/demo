package com.intertek.demo.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author jacksy.qin
 * @date 2019/9/23 13:31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ItsEndPoint {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
