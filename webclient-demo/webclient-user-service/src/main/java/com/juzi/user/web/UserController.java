package com.juzi.user.web;

import com.juzi.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author codejuzi
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/get/{id}")
    public User get(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setName("[GET]ZhangSan " + id);
        user.setAge(id.intValue());
        return user;
    }

    @GetMapping("/name")
    public String get(String name) {
        log.info("[GET] name = " + name);
        return "[GET] name = " + name;
    }

    @PostMapping("/post/{id}")
    public User post(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setName("[POST]List " + id);
        user.setAge(id.intValue());
        return user;
    }

}

