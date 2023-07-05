package com.juzi.api.service;

import com.juzi.api.pojo.User;

/**
 * @author codejuzi
 */
public interface UserService {
    String getUserName(User user);

    String sayHello(String name);
}
