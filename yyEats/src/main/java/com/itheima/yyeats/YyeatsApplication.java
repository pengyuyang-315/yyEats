package com.itheima.yyeats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class YyeatsApplication {
    public static void main(String[] args) {
        SpringApplication.run(YyeatsApplication.class,args);
        log.info("start successful");
    }
}
