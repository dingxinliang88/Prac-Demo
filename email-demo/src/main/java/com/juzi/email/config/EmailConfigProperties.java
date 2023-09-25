package com.juzi.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author codejuzi
 */
@ConfigurationProperties(prefix = "spring.email")
@Configuration
@Data
public class EmailConfigProperties {

    private String from = "codejuzi@qq.com";
}
