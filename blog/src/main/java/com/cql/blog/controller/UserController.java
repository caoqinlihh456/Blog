package com.cql.blog.controller;


import com.cql.commons.moudel.system.Result;
import com.cql.user.entity.User;
import com.cql.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  //
@RequestMapping("/user")
@Validated
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/test")
    public Result<Boolean> test(){
//        System.out.println(dog);
//        System.out.println(cat);
        log.debug("deg11");
        log.info("info11");
        log.warn("warn11");
        log.error("error11");
        log.trace("trace11");
        return Result.genSuccessResult("操作成功",true);
    }

    @GetMapping("/add")
    public Result<Boolean> add(){
        User user = new User();
        user.setName("哈哈哈啊哈哈");
        userService.save(user);
        return Result.genSuccessResult("操作成功", true);
    }

    @GetMapping("/thymeleafTest")
    public String add(Model model){
        //数据存入model
        model.addAttribute("name","cql在学习");
        //返回test.html
        return "test";
    }


}
