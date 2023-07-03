<h4 align="right"><a href="./README.md">
English</a> | <strong>简体中文</strong></h4>



<h1 align="center">MinIO-File-Stater</h1>
<p align="center"><strong>Minio文件启动器是一个Java库，为与Minio对象存储系统进行交互提供文件存储服务接口。它使您能够轻松地上传、下载和删除Minio服务器中的文件。</strong></p>



<p align="center"> <a href="https://maven.apache.org/"><img src="https://img.shields.io/badge/Maven-3.8.3-blue.svg" alt="Maven版本"></a> <a href="https://www.oracle.com/java/technologies/javase-jdk11-downloads.html"><img src="https://img.shields.io/badge/JDK-11-orange.svg" alt="JDK版本"></a> <a href="https://min.io/"><img src="https://img.shields.io/badge/MinIO-RELEASE.8.5.2-blue.svg" alt="MinIO版本"></a> <a href="https://codejuzi.icu/"><img src="https://img.shields.io/badge/Blog-codejuzi.icu-yellowgreen.svg" alt="我的博客"></a> </p>

## 功能特点

- 上传文件：`uploadFile` 方法允许您将文件上传到Minio服务器。您可以指定前缀、文件名、文件的输入流和文件类型。它返回上传文件的完整路径。
- 上传图片文件：`uploadImage` 方法是一个专门用于上传图片文件的便捷方法。它接受与 `uploadFile` 方法类似的参数，但不需要显式指定文件类型。
- 上传HTML文件：`uploadHtml` 方法是另一个方便的上传HTML文件的方法。和 `uploadImage` 方法一样，它简化了流程，无需显式指定文件类型。
- 删除文件：`delete` 方法允许您从Minio服务器删除文件。您需要提供要删除的文件的完整路径。
- 下载文件：`downloadFile` 方法使您能够从Minio服务器下载文件。您提供文件的完整路径，它返回包含文件数据的输入流。

## 依赖项

Minio文件启动器需要以下依赖项：

- Java 11 或更高版本

请确保在项目的构建配置中包含这些依赖项。

## 快速开始

1. 引入依赖

   ```xml
   <dependency>
       <groupId>icu.codejuzi</groupId>
       <artifactId>minio-file-starter</artifactId>
       <version>0.0.1</version>
   </dependency>
   ```

2. 修改配置

	```yaml
	minio:
	  access-key: QQefvRv3XMNMHWEqTgMx
	  secret-key: 05Gxf0TSaxo83w28m6CN7ZQegWd9h3PkMIXnYxTh
	  end-point: http://10.211.55.3:9000
	  read-path: http://10.211.55.3:9000
	  bucket: minio-demo
	```

3. 注入客户端，开始使用

	```java
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
	```

	

## 许可证

Minio文件启动器库在MIT许可下发布。有关详细信息，请参阅 [LICENSE](https://chatbot.theb.ai/link-to-license-file) 文件。

## 贡献

欢迎为Minio文件启动器项目做出贡献！如果您发现任何问题或有改进建议，请随时创建拉取请求或在项目的GitHub存储库上提交问题。

## 联系方式

如果您有任何问题或需要进一步帮助，请联系codejuzi，邮箱为 [dingxinliang1118@gmail.com]。

使用Minio文件启动器愉快编码！