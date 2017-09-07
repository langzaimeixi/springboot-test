package com.lilang.springboot.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * User: lilang Date: 2017/8/28 ProjectName: springboot-test
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.lilang.springboot.controller," +
        "com.lilang.springboot.service,com.lilang.springboot.cache,com.lilang.springboot.localcache"})
@MapperScan(basePackages = {"com.lilang.springboot.dao"})
@ImportResource("classpath:applicationContext*.xml")
public class ApplicationRun  {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }

}
