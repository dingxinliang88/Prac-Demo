<h4 align="right"><strong>English</strong> | <a href="./README_CN.md">
简体中文</a></h4>



<h1 align="center">MinIO-File-Stater</h1>
<p align="center"><strong>The Minio File Starter is a Java library that provides a file storage service interface for interacting with the Minio object storage system. It enables you to easily upload, download, and delete files from a Minio server.</strong></p>

<p align="center">
  <a href="https://maven.apache.org/"><img src="https://img.shields.io/badge/Maven-3.8.3-blue.svg" alt="Maven Version"></a>
  <a href="https://www.oracle.com/java/technologies/javase-jdk11-downloads.html"><img src="https://img.shields.io/badge/JDK-11-orange.svg" alt="JDK Version"></a>
  <a href="https://min.io/"><img src="https://img.shields.io/badge/MinIO-RELEASE.8.5.2-blue.svg" alt="MinIO Version"></a>
  <a href="https://codejuzi.icu/"><img src="https://img.shields.io/badge/Blog-codejuzi.icu-yellowgreen.svg" alt="My Blog"></a>
</p>

## Features

- Upload files: The `uploadFile` method allows you to upload a file to the Minio server. You can specify a prefix, file name, input stream of the file, and file type. It returns the full path of the uploaded file.
- Upload image files: The `uploadImage` method is a convenience method specifically designed for uploading image files. It takes similar parameters as the `uploadFile` method but does not require specifying the file type explicitly.
- Upload HTML files: The `uploadHtml` method is another convenient method for uploading HTML files. Like the `uploadImage` method, it simplifies the process by not requiring the explicit specification of the file type.
- Delete files: The `delete` method allows you to delete a file from the Minio server. You need to provide the full path of the file to be deleted.
- Download files: The `downloadFile` method enables you to download a file from the Minio server. You provide the full path of the file, and it returns an input stream containing the file data.

## Dependencies

The Minio File Starter requires the following dependencies:

- Java 11 or higher

Make sure to include these dependencies in your project's build configuration.

## Quick Start

1. Add dependencies.

	```xml
	<dependency>
        <groupId>icu.codejuzi</groupId>
        <artifactId>minio-file-starter</artifactId>
        <version>0.0.1</version>
    </dependency>
	```

2. Modify the configuration.

   ```yaml
   minio:
     access-key: QQefvRv3XMNMHWEqTgMx
     secret-key: 05Gxf0TSaxo83w28m6CN7ZQegWd9h3PkMIXnYxTh
     end-point: http://10.211.55.3:9000
     read-path: http://10.211.55.3:9000
     bucket: minio-demo
   ```

3. Inject the client and start using it.

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

## License

The Minio File Starter library is released under the MIT License. See the [LICENSE](https://chatbot.theb.ai/link-to-license-file) file for more details.

## Contribution

Contributions to the Minio File Starter project are welcome! If you find any issues or have suggestions for improvements, feel free to create a pull request or submit an issue on the project's GitHub repository.

## Contact

If you have any questions or need further assistance, please contact codejuzi at [dingxinliang1118@gmail.com].

Happy coding with the Minio File Starter!