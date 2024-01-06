package com.itheima.yyeats;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
@MapperScan("com.itheima.yyeats.mapper")
@ServletComponentScan
public class YyeatsApplication {
    public static void main(String[] args) {
        SpringApplication.run(YyeatsApplication.class,args);
        log.info("start successful");
    }
}
