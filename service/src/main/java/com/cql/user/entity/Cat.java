package com.cql.user.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component //标记spring组件
//@PropertySource(value = {"classpath:cat.properties"})  //多个
@PropertySource("cat.properties")//单个引入这个配置文件   为了分离配置文件 好维护
@ConfigurationProperties(prefix = "cat")//使用配置类 引入前缀 为cat    这个必须要
@Data
public class Cat {

    private String name;

    private int age;

}
