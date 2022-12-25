package com.teng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.teng.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("小王", "http://47.100.85.148/myBlog", "2073894727@qq.com");
        return new ApiInfoBuilder()
                .title("小王Blog")
                .description("小王Blog后端接口文档")
                .contact(contact)   // 联系方式
                .version("1.1.0")  // 版本
                .build();
    }
}