package com.learn.management;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * SpringBoot 启动类
 */
@SpringBootApplication
public class LearnManagementSystemApplication{

    public static void main(String[] args) {
        SpringApplication.run(LearnManagementSystemApplication.class, args);
    }


}
