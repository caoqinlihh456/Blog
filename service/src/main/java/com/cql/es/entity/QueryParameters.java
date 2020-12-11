package com.cql.es.entity;

import lombok.Data;

import java.util.List;

/**
 * es查询参数
 */
@Data
public class QueryParameters {

    /**
     * 字段查询类型集合
     */
    private List<Condition> conditionList;


    /**
     * 排序字段集合
     */
    private List<Sort> sortList;


    /**
     * 当前页( 小于等于 0    则不分页)
     */
    private int pageNo = 0;

    /**
     * 每页记录数
     */
    private int pageSize;

}
