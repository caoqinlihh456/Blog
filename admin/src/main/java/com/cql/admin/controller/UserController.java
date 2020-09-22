package com.cql.admin.controller;


import com.cql.commons.moudel.Result;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/configure/test")
    public Result<Boolean> test(){
        System.out.println(dog);
        System.out.println(cat);
        log.debug("deg");
        log.info("info");
        log.warn("warn");
        log.error("error");
        log.trace("trace");
        return Result.genSuccessResult("操作成功",true);
    }

    /**
     * 测试缓存     查询 更新 删除
     * @return
     */
    @GetMapping("/redis/query")
    @Cacheable(cacheNames = "user")
    public Result<User> queryRedis(@RequestParam Long id){
        System.out.println(dog);
        User user = userService.getById(id);
        return Result.genSuccessResult("操作成功",user);
    }

    @GetMapping("/redis/update")
//    @CachePut(cacheNames = "user",key = "#id") //编辑缓存
    @CacheEvict(cacheNames = "user",key = "#id")//删除缓存
    public Result<Boolean> updateQuery(@RequestParam Long id){
        User user = new User();
        user.setId(1L);
        user.setName("修改缓存");
        userService.updateById(user);
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
