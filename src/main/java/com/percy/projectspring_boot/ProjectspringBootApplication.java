package com.percy.projectspring_boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.percy.projectspring_boot.mapper")
public class ProjectspringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectspringBootApplication.class, args);
    }



}
