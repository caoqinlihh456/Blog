package com.cql.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "goodses1", type = "goodses1")
@Data
public class GoodsES {

    private String id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Double)
    private Double price;

    //如果是字符串的话  还是要带分词的
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String brand;

}
