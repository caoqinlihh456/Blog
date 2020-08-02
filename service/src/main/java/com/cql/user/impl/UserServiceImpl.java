package com.cql.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cql.user.entity.User;
import com.cql.user.mapper.UserMapper;
import com.cql.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
