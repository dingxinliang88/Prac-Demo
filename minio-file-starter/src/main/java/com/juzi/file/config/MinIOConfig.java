package com.juzi.file.config;

import com.juzi.file.service.FileStorageService;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * minio 配置
 *
 * @author codejuzi
 */
@Data
@Configuration
@EnableConfigurationProperties({MinIOConfigProperties.class})
@ConditionalOnClass({FileStorageService.class})
public class MinIOConfig {

    @Resource
    private MinIOConfigProperties minIOConfigProperties;

    /**
     * 构建minio客户段
     *
     * @return minio client
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .credentials(minIOConfigProperties.getAccessKey(), minIOConfigProperties.getSecretKey())
                .endpoint(minIOConfigProperties.getEndPoint())
                .build();
    }
}
