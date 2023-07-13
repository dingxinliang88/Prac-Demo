package com.juzi.auth.controller;

import com.juzi.auth.pojo.entity.User;
import com.juzi.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author codejuzi
 */

@RestController
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login-success")
    public String loginSuccess() {
        return "登录成功";
    }

    @GetMapping("/user/{id}")
    public User getuser(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping("/r/r1")
    @PreAuthorize("hasAuthority('p1')")
    public String r1() {
        return "访问【r1】资源";
    }

    @GetMapping("/r/r2")
    @PreAuthorize("hasAuthority('p2')")
    public String r2() {
        return "访问【r2】资源";
    }
}
