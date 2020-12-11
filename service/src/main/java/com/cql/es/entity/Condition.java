package com.cql.es.entity;

import lombok.Data;

@Data
public class Condition {

    /**
     * 操作类型  eq  like
     */
    private String name;

    /**
     * 属性
     */
    private Attribute attribute;

}
