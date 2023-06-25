package com.juzi.file.service;

import java.io.InputStream;

/**
 * 文件存储服务接口
 *
 * @author codejuzi
 */
public interface FileStorageService {

    /**
     * 上传文件
     *
     * @param prefix      文件名前缀
     * @param fileName    文件名
     * @param inputStream 文件流
     * @param type        文件类别
     * @return 文件全路径
     */
    String uploadFile(String prefix, String fileName, InputStream inputStream, String type);


    /**
     * 上传图片文件
     *
     * @param prefix      文件名前缀
     * @param fileName    文件名
     * @param inputStream 文件流
     * @return 文件全路径
     */
    String uploadImage(String prefix, String fileName, InputStream inputStream);

    /**
     * 上传HTML文件
     *
     * @param prefix      文件名前缀
     * @param fileName    文件名
     * @param inputStream 文件流
     * @return 文件全路径
     */
    String uploadHtml(String prefix, String fileName, InputStream inputStream);

    /**
     * 删除文件
     *
     * @param filePathUrl 文件全路径
     */
    void delete(String filePathUrl);

    /**
     * 下载文件
     *
     * @param filePathUrl 文件全路径
     * @return 文件流
     */
    InputStream downloadFile(String filePathUrl);

}
