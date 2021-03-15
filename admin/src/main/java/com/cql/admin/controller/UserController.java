package com.cql.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cql.commons.group.Add;
import com.cql.commons.group.Update;
import com.cql.commons.moudel.system.Result;
import com.cql.user.entity.Cat;
import com.cql.user.entity.Dog;
import com.cql.user.entity.User;
import com.cql.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController  //
@RequestMapping("/user")
@Validated
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Dog dog;  //注入dog配置

    @Autowired
    private Cat cat;  //注入配置


    @PostMapping("/configure/test")
    public Result<Boolean> test(@RequestBody @Validated({Add.class, Update.class}) User user) {
//        log.debug("deg");
//        log.info("info");
//        log.warn("warn");
//        log.error("error");
//        log.trace("trace");
        return Result.genSuccessResult("操作成功", true);
    }

    /**
     * 测试缓存     查询 更新 删除
     *
     * @return
     */
    @GetMapping("/redis/query")
    @Cacheable(cacheNames = "user", key = "#id + '-'+#id2")
    public Result<Map<String, User>> queryRedis(@RequestParam Long id, Long id2) {
        System.out.println(dog);
        User user = userService.getById(id);
        return Result.genSuccessResult("操作成功", new HashMap<>());
    }

    @GetMapping("/redis/query2")
    @Cacheable(cacheNames = "user2", key = "#id + '-'+#id2")
    public Result<Map<String, User>> queryRedis2(@RequestParam Long id, Long id2) {
        System.out.println(dog);
        User user = userService.getById(id);
        HashMap<String, User> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("1", new User());
        return Result.genSuccessResult("操作成功", objectObjectHashMap);
    }


    @GetMapping("/redis/update")
//    @CachePut(cacheNames = "user",key = "#id") //编辑缓存
    @CacheEvict(cacheNames = {"user"},allEntries = true)//删除缓存
    public Result<Boolean> updateQuery(@RequestParam Long id, Long id2) {
        User user = new User();
        user.setId(1L);
        user.setName("修改缓存");
        userService.updateById(user);
        IPage<User> page = userService.page(new Page<>(1, 0));
        return Result.genSuccessResult("操作成功", true);
    }


    @GetMapping("/add")
    public Result<Boolean> add() {
        User user = new User();
        user.setName("哈哈哈啊哈哈");
        userService.save(user);
        return Result.genSuccessResult("操作成功", true);
    }

    @GetMapping("/thymeleafTest")
    public String add(Model model) {
        //数据存入model
        model.addAttribute("name", "cql在学习");
        //返回test.html
        return "test";
    }


}
