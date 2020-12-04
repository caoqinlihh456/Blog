package com.cql.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {

    @TableField
    private Long id;

    private String name;

    private String password;

    @TableField(exist = false)
    private String token;


}
