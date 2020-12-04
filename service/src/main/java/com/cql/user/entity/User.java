package com.cql.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;


/**
 * @author Administrator
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1966316490796555921L;

    @TableField
    private Long id;

    private String name;

    private String password;

    @TableField(exist = false)
    private String token;


}
