package com.hsyou.wagupost.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsyou.wagupost.model.CommentDTO;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EurekaClient discoveryClient;

    @HystrixCommand(fallbackMethod = "reliable")
    public List<CommentDTO> requestComment(long postId){
        String url = discoveryClient.getNextServerFromEureka("wagu-comment", false).getHomePageUrl();
        String response = restTemplate.getForObject (url+"/"+postId, String.class);

        try {
            return Arrays.asList(objectMapper.readValue(response, CommentDTO[].class));
//            return Collections.emptyList();
        } catch (Exception ex){
            return Collections.emptyList();
        }
    }

    public List<CommentDTO> reliable(long id){
        return Collections.emptyList();
    }


}
