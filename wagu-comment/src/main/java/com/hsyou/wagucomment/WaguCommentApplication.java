package com.hsyou.wagucomment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WaguCommentApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaguCommentApplication.class, args);
    }

}

