package com.cql.user.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component //标记spring组件
@ConfigurationProperties(prefix = "dog")//使用配置类 引入前缀 为dog
@Data
public class Dog {

    private String name;
}
