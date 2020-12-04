package com.cql.commons.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {


    public static void main(String[] args) {
        String userName = getUserName("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDY4ODczNzcsInVzZXJJZCI6IjMiLCJ1c2VybmFtZSI6ImNxbCJ9.kkCjYU_ENJAxxNyameWe4ZeCBz28f2vWGynNcAcZM7o");
        System.out.println(userName);
        System.out.println(getUserId("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDY4ODczNzcsInVzZXJJZCI6IjMiLCJ1c2VybmFtZSI6ImNxbCJ9.kkCjYU_ENJAxxNyameWe4ZeCBz28f2vWGynNcAcZM7o"));
//        String cql = sign("cql", "3");
//        System.out.println(cql);
    }

    /**
     * 过期时间为一天
     * TODO 正式上线更换为15分钟
     */
//    public static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;
    public static final long EXPIRE_TIME = 1000 * 60;

    /**
     * token私钥
     */
    public static final String TOKEN_SECRET = "cql";

    // 请求头
    public static final String AUTH_HEADER = "X-Authorization-Token";

    /**
     * 生成签名,15分钟后过期
     *
     * @param username
     * @param userId
     * @return
     */
    public static String sign(String username, String userId) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //附带username和userID生成签名
        return JWT.create().withHeader(header).withClaim("username", username)
                .withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
    }

    public static boolean verity(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public static String getUserName(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (jwt == null) {
                throw new Exception("token失效");
            }
            return jwt.getClaims().get("username").asString();
        } catch (Exception e) {
        }
        return null;
    }

    public static String getUserId(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (jwt == null) {
                throw new Exception("token失效");
            }
            return jwt.getClaims().get("userId").asString();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 刷新 token的过期时间
     */
    public static String refreshTokenExpired(String token, String secret) {
        DecodedJWT jwt = JWT.decode(token);
        Map<String, Claim> claims = jwt.getClaims();
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTCreator.Builder builer = JWT.create().withExpiresAt(date);
            for (Map.Entry<String, Claim> entry : claims.entrySet()) {
                builer.withClaim(entry.getKey(), entry.getValue().asString());
            }
            return builer.sign(algorithm);
        } catch (JWTCreationException e) {
            return null;
        }
    }


}
