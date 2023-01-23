package com.codeup.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.codeup.spring")
@EntityScan("com.codeup.spring.models")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
