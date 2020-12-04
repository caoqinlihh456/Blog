package com.cql.admin.controller;


import com.cql.commons.moudel.system.Result;
import com.cql.user.entity.User;
import com.cql.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController  //
@Validated
@Slf4j
public class AsyncController {

    @Autowired
    private UserService userService;

    @GetMapping("/asyns/test")
//    @RequiresRoles(value = {"admin","youhu"})//角色
//    @RequiresPermissions("user:delete")//权限
    public Result<Map<String,User>> test() {
//        Boolean delete = userService.delete();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        System.out.println(user);
        return Result.genSuccessResult("操作成功", new HashMap<>());
    }
}
