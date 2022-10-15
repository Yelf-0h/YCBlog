package com.yecheng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger配置
 *
 * @author Yefl
 * @date 2022/10/15
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yecheng.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("叶橙", "https://github.com/Yelf-0h", "banmianye@gmail.com");
        return new ApiInfoBuilder()
                .title("叶橙博客接口文档")
                .description("此文档为叶橙博客的接口文档，仅用于学习")
                // 联系方式
                .contact(contact)
                // 版本
                .version("0.0.1")
                .build();
    }
}