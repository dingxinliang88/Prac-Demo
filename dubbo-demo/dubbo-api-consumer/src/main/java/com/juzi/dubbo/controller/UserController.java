package com.juzi.dubbo.controller;

import com.juzi.api.pojo.User;
import com.juzi.api.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codejuzi
 */
@RestController
public class UserController {

    @DubboReference
    private UserService userService;

    @GetMapping("/user")
    public String getUserName(User user) {
        return userService.getUserName(user);
    }

    @GetMapping("/say")
    public String sayHello(String name) {
        return userService.sayHello(name);
    }
}
