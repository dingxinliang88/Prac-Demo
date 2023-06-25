package com.juzi.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * minio配置项
 *
 * @author codejuzi
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinIOConfigProperties implements Serializable {

    private static final long serialVersionUID = -5360946810187104229L;

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String endPoint;
    private String readPath;

}
