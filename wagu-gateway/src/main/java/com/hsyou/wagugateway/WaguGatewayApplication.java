package com.hsyou.wagugateway;

import com.netflix.discovery.EurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableZuulProxy
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
public class WaguGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaguGatewayApplication.class, args);
    }

    /*
        Custom Fallback
     */
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WaguFallbackProvider waguFallbackProvider() {
        WaguFallbackProvider route1ZuulFallback = new WaguFallbackProvider();
        return route1ZuulFallback;
    }

    /*
    Enabling Cross Origin Requests
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST","PUT","DELETE");
            }
        };
    }
}

