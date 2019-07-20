package com.wsy.mybatis_plus_demo.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.wsy.mybatis_plus_demo.mapper")
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor page =new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
}
