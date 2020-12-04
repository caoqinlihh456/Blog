package com.cql.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.cql.admin.shiro.ShiroUtils;
import com.cql.commons.moudel.shiro.LoginType;
import com.cql.commons.moudel.shiro.UsernamePasswordToken;
import com.cql.commons.moudel.system.Result;
import com.cql.commons.util.JWTUtil;
import com.cql.commons.util.RedisUtil;
import com.cql.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@Validated
@Slf4j
public class LoginController {


    @Autowired
    private RedisUtil redisUtil;


    /**
     * 登录逻辑处理
     */
    @RequestMapping("/login")
    public Result<User> login(@NotBlank(message = "name不能为空") String name, @NotBlank(message = "password不能为空") String password) {

        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(LoginType.USER_EMAIL, name, password);
        //3.执行登录方法
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败:用户名不存在
            return Result.genErrorResult(-200, "用户名不存在");
        } catch (IncorrectCredentialsException e) {
            //e.printStackTrace();
            //登录失败:密码错误
            return Result.genErrorResult(-200, "密码错误");
        }
        //登录成功
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //生成token 保存到redis 作为key
        String jwtToken = JWTUtil.sign(user.getName(), user.getId().toString());
        user.setToken(jwtToken);
        redisUtil.set(jwtToken, JSONObject.toJSONString(user), JWTUtil.EXPIRE_TIME);
        return Result.genSuccessResult("登录成功", user);
    }

    @GetMapping("/notLogged")
    public Result notLogged() {
        return Result.genErrorResult(-200, "未登录");
    }

    @GetMapping("/noAuth")
    public Result noAuth() {
        return Result.genErrorResult(-200, "权限不足");
    }

    @PostMapping("/logout")
    public Result<Boolean> logout() {
        User user = ShiroUtils.getUserInfo();
        redisUtil.delete(user.getToken());
        ShiroUtils.logout();
        return Result.genSuccessResult("退出登录", true);
    }


}
