package com.yecheng;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 主要
 *
 * @author Yefl
 * @date 2022/10/12
 */
@SpringBootApplication
@MapperScan("com.yecheng.mapper")
@EnableScheduling
@EnableSwagger2
public class YeChengBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(YeChengBlogApplication.class,args);
    }
}