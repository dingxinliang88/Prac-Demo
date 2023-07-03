# Nacos 实战

> 官网：https://nacos.io/
>
> GitHub地址：https://github.com/alibaba/nacos

## 简介

> 官方文档：https://nacos.io/zh-cn/docs/v2/what-is-nacos.html

官网简介：一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。（an easy-to-use dynamic service discovery, configuration and service management platform for building cloud native applications.）

Nacos是SpringCloudAlibaba的组件，而SpringCloudAlibaba也遵循SpringCloud中定义的服务注册、服务发现规范。



## 安装

> 参照官方文档：https://nacos.io/zh-cn/docs/v2/quickstart/quick-start.html

**Mac（Arm64架构）安装**

预先环境：

- JDK1.8 + 
- Maven 3.2.x +

方式一：编译安装

```sh
git clone https://github.com/alibaba/nacos.git
cd nacos/
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U  
ls -al distribution/target/

// change the $version to your actual path
cd distribution/target/nacos-server-$version/nacos/bin
```

方式二：压缩包安装

[官网](https://github.com/alibaba/nacos/releases)下载编译后的安装包`nacos-server-$version.zip`，解压即可

```sh
unzip nacos-server-$version.zip 或者 tar -xvf nacos-server-$version.tar.gz

cd nacos/bin
```

启动nacos

```sh
sh startup.sh -m standalone
```

查看启动日志

```sh
cat ../logs/start.out
```

访问地址：http://localhost:8848即可



## 服务注册

1）父工程引入依赖：

```xml
<dependencyManagement>
  <dependencies>
    <!-- ... -->
    <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-alibaba-dependencies</artifactId>
      <version>2021.0.5.0</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

2）子工程引入服务发现依赖

```xml
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

3）书写nacos配置

```yaml
spring:
  cloud:
    nacos:
      server-addr: 192.168.1.7:8848
```

> 上述地址最好写成ip:port的形式（详见启动日志），集群模式下这样写不会串

4）重启对应子模块的服务

重启微服务后，登录nacos管理页面，可以看到微服务信息



## 配置中心

1）在nacos中添加配置文件

> 注意：项目的核心配置，需要热更新的配置才有放到nacos管理的必要。基本不会变更的一些配置还是保存在微服务本地比较好。

2）本地拉取配置

微服务要拉取nacos中管理的配置，并且与本地的application.yml配置合并，才能完成项目启动。

但如果尚未读取application.yml，又如何得知nacos地址呢？

因此spring引入了一种新的配置文件：bootstrap.yaml文件，会在application.yml之前被读取，流程如下：



![L0iFYNF](assets/L0iFYNF-8371530.png)



引入依赖

```xml
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-bootstrap</artifactId>
</dependency>
```

创建`bootstrap.yaml`文件

```yaml
spring:
  application:
    name: order-service
  profiles:
    active: dev
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.1.7:8848
      config:
        file-extension: yaml
```

这里会根据spring.cloud.nacos.server-addr获取nacos地址，再根据`${spring.application.name}-${spring.profiles.active}.${spring.cloud.nacos.config.file-extension}`作为文件id，来读取配置。

3）配置热更新

目的：修改nacos中的配置后，微服务中无需重启即可让配置生效，也就是**配置热更新**

方式一：在`@Value`注入的变量所在类上添加注解`@RefreshScope`

方式二：使用`@ConfigurationProperties`注解代替`@Value`注解，即创建配置类



4）配置共享

其实微服务启动时，会去nacos读取多个配置文件

- `[spring.application.name]-[spring.profiles.active].yaml`

- `[spring.application.name].yaml`

而`[spring.application.name].yaml`不包含环境，因此可以被多个环境共享。

配置共享的优先级：

![image-20210714174623557](assets/image-20210714174623557-8371530.png)



## Nacos + OpenFeign实现RPC

1）抽取出feign-api模块

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>com.juzi</groupId>
    <artifactId>nacos-demo</artifactId>
    <version>1.0-SNAPSHOT</version>
  </parent>

  <artifactId>feign-api</artifactId>

  <properties>
    <java.version>11</java.version>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
  </dependencies>

</project>
```

UserClient.java

```java
package com.juzi.feign.clients;

import com.juzi.feign.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service")
public interface UserClient {

  @GetMapping("/user/{id}")
  User findById(@PathVariable("id") Long id);
}
```

DefaultFeignConfiguration.java

```java
package com.juzi.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfiguration {
  @Bean
  public Logger.Level logLevel() {
    return Logger.Level.BASIC;
  }
}
```



2）order-service模块

引入依赖

```xml
<dependency>
  <groupId>com.juzi</groupId>
  <artifactId>feign-api</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

修改依赖

```xml
<dependency>
  <groupId>com.alibaba.cloud</groupId>
  <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  <!--            不使用Ribbon 进行客户端负载均衡-->
  <exclusions>
    <exclusion>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
    </exclusion>
  </exclusions>
</dependency>
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

修改主类

```java
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

```

使用feign实现RPC

```java
package com.juzi.order.service;

import com.juzi.feign.clients.UserClient;
import com.juzi.feign.pojo.User;
import com.juzi.order.mapper.OrderMapper;
import com.juzi.order.pojo.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService {

  @Resource
  private OrderMapper orderMapper;

  @Resource
  private UserClient userClient;

  public Order queryOrderById(Long orderId) {
    // 1.查询订单
    Order order = orderMapper.findById(orderId);
    // 2.查询用户
    User user = userClient.findById(order.getUserId());
    // 3.设置
    order.setUser(user);
    // 4.返回
    return order;
  }
}
```

