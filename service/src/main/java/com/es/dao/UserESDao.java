package com.es.dao;

import com.es.entity.UserES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserESDao extends ElasticsearchRepository<UserES, String> {

}
