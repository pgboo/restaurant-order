package com.restaurant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("餐饮管理系统 API文档")
                        .description("基于Spring Boot 3.x的餐饮管理系统接口文档")
                        .version("1.0.0")
                        .contact(new Contact().name("Restaurant Admin")));
    }
}
