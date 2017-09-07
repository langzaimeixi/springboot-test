package com.lilang.springboot.cache.annotation;

import java.lang.annotation.*;

/**
 * ${DESCRIPTION}
 * User: lilang
 * Date: 2017/9/6 ProjectName: springboot-test Version：5.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheEvit {

    String key() default "";

    String[] keys() default {};

}
