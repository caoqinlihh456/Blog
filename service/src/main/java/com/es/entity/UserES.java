package com.es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author Administrator
 */
//通过这个注解可以声明一个文档，指定其所在的索引库和type
@Document(indexName = "userdoct", type = "user")
@Data
public class UserES implements Serializable {

    @Id
    private String id;

    // 这里配置了分词器，字段类型，可以不配置，默认也可
//    @Field(analyzer = "ik_smart", type = FieldType.Text)
    @Field(type = FieldType.Text)
    private String name;

    private Integer age;

    @Field(type = FieldType.Text)
    private String sex;

    @Field(type = FieldType.Text)
    private String desc;


}
