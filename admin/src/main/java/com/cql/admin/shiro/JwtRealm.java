package com.cql.admin.shiro;

import com.alibaba.fastjson.JSONObject;
import com.cql.commons.exception.ServiceException;
import com.cql.commons.moudel.shiro.LoginType;
import com.cql.commons.util.JWTUtil;
import com.cql.commons.util.RedisUtil;
import com.cql.user.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
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
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        System.out.println("执行JwtRealm授权逻辑");

        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //添加资源的授权字符串
        //info.addStringPermission("user:add");

        //到数据库查询当前登录用户的授权字符串
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
//		info.addStringPermission(dbUser.getPerms());

        return info;
    }


    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        System.out.println("执行JwtRealm认证逻辑");

        //编写shiro判断逻辑，判断用户名和密码
        //1.判断用户名
        com.cql.commons.moudel.shiro.UsernamePasswordToken token = (com.cql.commons.moudel.shiro.UsernamePasswordToken) arg0;
        if (!JWTUtil.verity(token.getToken())) {
            throw new AuthenticationException("token校验失败");
        }
        //获取redis中数据
        String json = (String) redisUtil.get(token.getToken());
        if (json == null) {
            throw new AuthenticationException("redis中token数据已失效");
        }
        User user = JSONObject.parseObject(json, User.class);
        return new SimpleAuthenticationInfo(user, "", getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof com.cql.commons.moudel.shiro.UsernamePasswordToken) {
            return ((com.cql.commons.moudel.shiro.UsernamePasswordToken) token).getLoginType() == LoginType.JWT_LOGIN;
        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        return LoginType.JWT_LOGIN.getType();
    }


}
