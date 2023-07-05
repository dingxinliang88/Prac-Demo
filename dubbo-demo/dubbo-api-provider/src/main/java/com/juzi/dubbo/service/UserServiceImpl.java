package com.juzi.dubbo.service;

import com.juzi.api.pojo.User;
import com.juzi.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author codejuzi
 */
@Slf4j
@DubboService
public class UserServiceImpl implements UserService {

    @Override
    public String getUserName(User user) {
        log.info("getUserName: {}", user);
        return user.getName();
    }

    @Override
    public String sayHello(String name) {
        log.info("sayHello: {}", name);
        return "hello " + name;
    }
}
