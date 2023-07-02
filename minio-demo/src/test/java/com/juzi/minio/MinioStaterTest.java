package com.juzi.minio;

import com.juzi.file.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author codejuzi
 */
@SpringBootTest
public class MinioStaterTest {

    @Resource
    private FileStorageService fileStorageService;

    @Test
    public void testUpdateImgFile() {
        try {
            String imgPath = "/Users/codejuzi/Pictures/Bg/github-cover.jpg";
            FileInputStream fileInputStream = new FileInputStream(imgPath);
            String filePath = fileStorageService.uploadImage("", "github-cover", fileInputStream);
            System.out.println(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
