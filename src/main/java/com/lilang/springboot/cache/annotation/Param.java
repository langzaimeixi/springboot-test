package com.lilang.springboot.cache.annotation;

import java.lang.annotation.*;

/**
 * ${DESCRIPTION}
 * User: lilang
 * Date: 2017/9/6 ProjectName: springboot-test Version：5.0.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Param {
    String value();
}
