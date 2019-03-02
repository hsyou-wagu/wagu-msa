package com.hsyou.wagusearch.controller;

import com.hsyou.wagusearch.model.PostDTO;
import com.hsyou.wagusearch.model.SearchEntity;
import com.hsyou.wagusearch.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;


    @GetMapping("/")
    public List<SearchEntity> search(@RequestParam String key){
        return searchService.searchByHashtag(key);
    }
}
