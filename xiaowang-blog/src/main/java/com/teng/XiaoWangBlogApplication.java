package com.teng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author:xiaowang
 * @Data:2022/09/20/16:08 Description:
 */
@SpringBootApplication
@MapperScan("com.teng.mapper")
@EnableScheduling
@EnableSwagger2
public class XiaoWangBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaoWangBlogApplication.class,args);
    }
}
