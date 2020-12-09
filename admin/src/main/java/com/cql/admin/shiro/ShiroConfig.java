package com.cql.admin.shiro;

import com.cql.commons.moudel.shiro.LoginType;
import com.cql.commons.moudel.shiro.MyModularRealmAuthenticator;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Shiro的配置类
 *
 * @author lenovo
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加 jwt 专用过滤器，拦截除 /login 和 /logout 外的请求
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwtFilter", jwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //添加Shiro内置过滤器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *    常用的过滤器：
         *       anon: 无需认证（登录）可以访问
         *       authc: 必须认证才可以访问
         *       user: 如果使用rememberMe的功能可以直接访问
         *       perms： 该资源必须得到资源权限才可以访问
         *       roles: 该资源必须得到角色权限才可以访问
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/testThymeleaf", "anon");
        //放行login.html页面
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/es/**", "anon");
        filterChainDefinitionMap.put("/user/configure/test", "anon");
        filterChainDefinitionMap.put("/user/redis/query", "anon");
        filterChainDefinitionMap.put("/user/redis/update", "anon");
//        filterChainDefinitionMap.put("/asyns/test", "roles[admin]");
//        filterChainDefinitionMap.put("/admin/**", "roles[admin]");

        //授权过滤器
        //注意：当前授权拦截后，shiro会自动跳转到未授权页面
//		filterChainDefinitionMap.put("/add", "perms[user:add]");
//		filterChainDefinitionMap.put("/update", "perms[user:update]");

//        filterChainDefinitionMap.put("/**", "authc");
        filterChainDefinitionMap.put("/**", "jwtFilter,authc"); //设置过滤器进来

        // 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        //修改调整的登录页面
        shiroFilterFactoryBean.setLoginUrl("/notLogged");

        //设置未授权提示页面
//		shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");  设置没用  用异常过滤器统一返回消息
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        return shiroFilterFactoryBean;
    }

    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter();
    }


    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(modularRealmAuthenticator());
        //关联realm
//        securityManager.setRealm(userRealm);// 单个
        //关联多个realm
        List<Realm> realms = new ArrayList<>();
        realms.add(userRealm());
        realms.add(emailRealm());
        realms.add(jwtRealm());
        securityManager.setRealms(realms);
        return securityManager;
    }


    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * 自定义的Realm管理，主要针对多realm
     */
    @Bean
    public MyModularRealmAuthenticator modularRealmAuthenticator() {
        MyModularRealmAuthenticator customizedModularRealmAuthenticator = new MyModularRealmAuthenticator();
        // 设置realm判断条件
        customizedModularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return customizedModularRealmAuthenticator;
    }


    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setName(LoginType.USER_PASSWORD.getType());
        return userRealm;
    }


    /**
     * 创建Realm
     */
    @Bean(name = "emailRealm")
    public EmailRealm emailRealm() {
        EmailRealm emailRealm = new EmailRealm();
        emailRealm.setName(LoginType.USER_EMAIL.getType());
        return emailRealm;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "jwtRealm")
    public JwtRealm jwtRealm() {
        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setName(LoginType.JWT_LOGIN.getType());
        return jwtRealm;
    }


}
