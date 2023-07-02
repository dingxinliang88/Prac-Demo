package com.juzi.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.FileInputStream;

/**
 * @author codejuzi
 */
public class MinIOTest {
    public static void main(String[] args) {
        FileInputStream fileInputStream;
        try {

            fileInputStream = new FileInputStream("/Users/codejuzi/Pictures/Bg/code_bear.jpg");

            //1.创建minio链接客户端
            MinioClient minioClient = MinioClient.builder().credentials("QQefvRv3XMNMHWEqTgMx", "05Gxf0TSaxo83w28m6CN7ZQegWd9h3PkMIXnYxTh")
                    .endpoint("http://10.211.55.3:9000").build();
            //2.上传
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    // 文件名
                    .object("code_bear.jpg")
                    // 文件类型
                    .contentType("image/jpeg")
                    .bucket("minio-demo")
                    // 文件流
                    .stream(fileInputStream, fileInputStream.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);

            System.out.println("http://10.211.55.3:9000/minio-demo/code_bear.jpg");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
