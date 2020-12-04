package com.cql.user.helper;

import com.cql.commons.config.asyn.AsyncConfig;
import com.cql.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 异步service服务
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class AnyncService {

    @Autowired
    private UserService userService;

    @Async(AsyncConfig.ASYNC_EXECUTOR_PUBLIC)
    public String test (){
        System.out.println("进入异步方法:"+Thread.currentThread().getName());

        userService.removeById(2);
//        int a = 1/ 0;
        return "123";
    }


}
