package com.cql.admin.controller;


import com.cql.commons.moudel.Result;
import com.cql.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  //
@Validated
@Slf4j
public class AsyncController {

    @Autowired
    private UserService userService;

    @GetMapping("/asyns/test")
    public Result<Boolean> test(){
        Boolean delete = userService.delete();
        return Result.genSuccessResult("操作成功",true);
    }
}
