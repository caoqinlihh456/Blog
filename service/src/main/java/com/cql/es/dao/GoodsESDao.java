package com.cql.es.dao;

import com.cql.es.entity.GoodsES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsESDao extends ElasticsearchRepository<GoodsES, String> {


}
