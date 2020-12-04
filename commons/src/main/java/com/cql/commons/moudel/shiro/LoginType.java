package com.cql.commons.moudel.shiro;

/** 登录方式枚举类
 * @Author:yongbo.zhou
 * @Email:yongbo.zhou@druglots.cn
 * @Date:2019/10/18
 * @Version:1.0
 */
public enum LoginType {

    /**
     * 通用
     */
    COMMON("common_realm"),
    /**
     * 用户密码登录
     */
    USER_PASSWORD("user_password_realm"),
    /**
     * 手机验证码登录
     */
    USER_PHONE("user_phone_realm"),
    /**
     * 邮箱验证登录
     */
    USER_EMAIL("user_email_realm"),
    /**
     * 第三方登录(微信登录)
     */
    WECHAT_LOGIN("wechat_login_realm"),

    H5_LOGIN("h5_login_realm"),

    /**
     * jwt登录
     */
    JWT_LOGIN("jwt_login_realm");

    private String type;

    private LoginType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
