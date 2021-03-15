package com.cql.admin.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cql.commons.moudel.shiro.LoginType;
import com.cql.user.entity.User;
import com.cql.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm
 *
 * @author lenovo
 */
public class EmailRealm extends AuthorizingRealm {


    @Autowired
    private UserService userSerivce;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        System.out.println("执行授权逻辑111111111111111111111111");
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //到数据库查询当前登录用户的授权字符串
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //todo 业务获取权限或角色
        //添加资源的授权字符串
        info.addStringPermission("user:delete");
        info.addRole("admin");
        info.addRole("youhu");
        return info;
    }


    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        System.out.println("执行邮箱认证逻辑");

        //编写shiro判断逻辑，判断用户名和密码
        //1.判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) arg0;

        QueryWrapper<User> userQuery = new QueryWrapper<>();
        userQuery.eq("name", token.getUsername());
        User user = userSerivce.getOne(userQuery);

        if (user == null) {
            //用户名不存在
            throw new UnknownAccountException();
        }

        //2.判断密码
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof com.cql.commons.moudel.shiro.UsernamePasswordToken) {
            return ((com.cql.commons.moudel.shiro.UsernamePasswordToken) token).getLoginType() == LoginType.USER_EMAIL;
        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        return LoginType.USER_EMAIL.getType();
    }


}
