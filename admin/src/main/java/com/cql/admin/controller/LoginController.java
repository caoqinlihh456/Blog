package com.cql.admin.controller;


import com.cql.commons.moudel.Result;
import com.cql.user.entity.User;
import com.cql.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@Validated
public class LoginController {

    @Autowired
    private UserService userService;



    /**
     * 登录逻辑处理
     */
    @RequestMapping("/login")
    public Result<User> login(@NotBlank(message ="name不能为空" ) String name, @NotBlank(message = "password不能为空") String password){
        /**
         * 使用Shiro编写认证操作
         */
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        //3.执行登录方法
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败:用户名不存在
            return Result.genErrorResult(-200,"用户名不存在");
        }catch (IncorrectCredentialsException e) {
            //e.printStackTrace();
            //登录失败:密码错误
            return Result.genErrorResult(-200,"密码错误");
        }
        //登录成功
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return Result.genErrorResult(-200,"用户信息不存在");
        }

        return Result.genSuccessResult("登录成功",user);
    }

    @GetMapping("/notLogged")
    public Result notLogged(){
        return Result.genErrorResult(-200,"未登录" );
    }

    @GetMapping("/logout")
    public Result<Boolean> logout(){
        SecurityUtils.getSubject().logout();
        return Result.genSuccessResult("退出登录", true);
    }


}
