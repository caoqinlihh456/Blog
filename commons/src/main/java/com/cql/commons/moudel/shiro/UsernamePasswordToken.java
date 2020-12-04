package com.cql.commons.moudel.shiro;


import lombok.Data;

@Data
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

    /**
     * 登录方式
     */
    private LoginType loginType;

    /**
     * token
     */
    private String token;


    /**
     * @param loginType
     * @param username
     * @param password
     */
    public UsernamePasswordToken(LoginType loginType, final String username, final String password) {
        super(username, password);
        this.loginType = loginType;
    }

    /**
     */
    public UsernamePasswordToken(LoginType loginType, final String token,final String username, final String password) {
        super(username, password);
        this.loginType = loginType;
        this.token = token;
    }

}
