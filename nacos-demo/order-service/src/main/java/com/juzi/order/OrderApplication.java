package com.juzi.order;

import com.juzi.feign.clients.UserClient;
import com.juzi.feign.config.DefaultFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author codejuzi
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.juzi.feign.clients"},clients = UserClient.class, defaultConfiguration = DefaultFeignConfiguration.class)
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
