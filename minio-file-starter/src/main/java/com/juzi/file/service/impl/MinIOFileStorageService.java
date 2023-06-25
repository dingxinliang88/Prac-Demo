package com.juzi.file.service.impl;

import com.juzi.file.config.MinIOConfig;
import com.juzi.file.config.MinIOConfigProperties;
import com.juzi.file.service.FileStorageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * minio 操作文件实现类
 *
 * @author codejuzi
 */
@Slf4j
@EnableConfigurationProperties({MinIOConfigProperties.class})
@Import({MinIOConfig.class})
public class MinIOFileStorageService implements FileStorageService {

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinIOConfigProperties minIOConfigProperties;

    private static final String SEPARATOR = "/";
    private static final String IMAGE_TYPE = "image/jpeg";
    private static final String HTML_TYPE = "text/html";

    private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd";

    @Override
    public String uploadFile(String prefix, String fileName, InputStream inputStream, String type) {
        String filePath = buildFilePath(prefix, fileName);

        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType(type)
                    .bucket(minIOConfigProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);
            return minIOConfigProperties.getReadPath()
                    + SEPARATOR
                    + minIOConfigProperties.getBucket()
                    + SEPARATOR
                    + filePath;
        } catch (Exception e) {
            log.error("minio put file error, ", e);
            throw new RuntimeException("upload file filed");
        }
    }

    @Override
    public String uploadImage(String prefix, String fileName, InputStream inputStream) {
        return uploadFile(prefix, fileName, inputStream, IMAGE_TYPE);
    }

    @Override
    public String uploadHtml(String prefix, String fileName, InputStream inputStream) {
        return uploadFile(prefix, fileName, inputStream, HTML_TYPE);
    }

    @Override
    public void delete(String filePathUrl) {
        String key = filePathUrl.replace(minIOConfigProperties.getEndPoint(), "");
        int index = key.indexOf(SEPARATOR);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index + 1);

        // delete
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucket).object(filePath).build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            log.error("minio remove file error, pathUrl:{}", filePathUrl, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream downloadFile(String filePathUrl) {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(minIOConfigProperties.getBucket())
                .object(filePathUrl)
                .build();
        try {
            return minioClient.getObject(getObjectArgs);
        } catch (Exception e) {
            log.error("minio download file error, ", e);
            throw new RuntimeException(e);
        }

    }

    /**
     * 构建文件路径
     *
     * @param dirPath  文件夹路径名
     * @param fileName 文件名 eg: yyyy/mm/dd/file.jpg
     * @return 文件路径
     */
    private String buildFilePath(String dirPath, String fileName) {
        StringBuilder stringBuilder = new StringBuilder(50);
        if (!dirPath.isEmpty()) {
            stringBuilder.append(SEPARATOR).append(dirPath);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        String todayStr = sdf.format(new Date());
        stringBuilder.append(todayStr).append(SEPARATOR).append(fileName);
        return stringBuilder.toString();
    }
}
