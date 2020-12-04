package com.cql.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cql.user.entity.User;
import com.cql.user.helper.AnyncService;
import com.cql.user.mapper.UserMapper;
import com.cql.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AnyncService anyncService;

    @Override
    public Boolean delete() {
//        userMapper.deleteById(1);
//        String test = anyncService.test();
//        System.out.println("异步的返回调用:"+test);
//        int a = 1/ 0;

        return true;
    }
}
