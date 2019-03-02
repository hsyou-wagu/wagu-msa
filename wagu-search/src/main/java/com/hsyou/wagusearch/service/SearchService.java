package com.hsyou.wagusearch.service;

import com.hsyou.wagusearch.model.SearchEntity;
import com.hsyou.wagusearch.repository.SearchRepository;
import com.hsyou.wagusearch.model.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SearchService {

    @Autowired
    private SearchRepository searchRepository;

    public SearchEntity save(PostDTO postDTO){
        return searchRepository.save(postDTO.toSearchEntity());
    }

    public List<SearchEntity> searchByHashtag(String key){
        return searchRepository.findByRemovedFalseAndHashtagsMatches(key);
    }

}
