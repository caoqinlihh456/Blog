package com.cql.admin.controller;


import com.cql.commons.moudel.Result;
import com.cql.user.entity.User;
import com.cql.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController  //
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/test")
    public Result<List<User>> test(){
        List<User> list = userService.list();
        return Result.genSuccessResult("操作成功", list);
    }

    @GetMapping("/add")
    public Result<Boolean> add(){
        User user = new User();
        user.setName("哈哈哈啊哈哈");
        userService.save(user);
        return Result.genSuccessResult("操作成功", true);
    }


}
