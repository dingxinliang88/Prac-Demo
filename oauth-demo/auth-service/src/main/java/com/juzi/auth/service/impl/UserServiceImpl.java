package com.juzi.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juzi.auth.mapper.UserMapper;
import com.juzi.auth.pojo.entity.User;
import com.juzi.auth.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author codejuzi
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

}




