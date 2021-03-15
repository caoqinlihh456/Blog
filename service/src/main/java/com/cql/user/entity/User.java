package com.cql.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cql.commons.group.Update;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author Administrator
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1966316490796555921L;

    @TableField
    private Long id;

    @NotBlank(message = "name不能为空",groups = Update.class)
    private String name;

    @NotBlank(message = "password不能为空",groups = Update.class)
    private String password;

    @TableField(exist = false)
    private String token;

    @TableField(exist = false)
    @Max(value = 12,message = "最小12",groups = Update.class)
    @NotNull(message = "size不能为空",groups = Update.class)
    private Integer size;


}
