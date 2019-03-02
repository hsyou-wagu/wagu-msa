package com.hsyou.wagusearch.repository;

import com.hsyou.wagusearch.model.SearchEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

public interface SearchRepository extends ElasticsearchCrudRepository<SearchEntity, Integer> {

    List<SearchEntity> findByRemovedFalseAndHashtagsMatches(String search);
}
