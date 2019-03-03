package com.hsyou.wagusearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsyou.wagusearch.model.PostDTO;
import com.hsyou.wagusearch.model.SearchEntity;
import com.hsyou.wagusearch.repository.SearchRepository;
import com.hsyou.wagusearch.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchTest {

    @Autowired
    private SearchService searchService;
    @Autowired
    private SearchRepository repository;
    @Test
    public void 서치엔티티로만들기(){

        String hashtag = "#hashtag1##hash2#ha3#h#";
        PostDTO postDTO = PostDTO.builder()
                .accountId(1L)
                .contents("testing, test, tester")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .removed(false)
                .hashtag(hashtag)
                .id(1L)
                .build();

        SearchEntity searchEntity = postDTO.toSearchEntity();

        Arrays.stream(searchEntity.getHashtags()).forEach(System.out::println);

        assertThat(searchEntity.getHashtags().length).isEqualTo(4);
        assertThat(searchEntity.getHashtags()[0]).isEqualTo("hashtag1");
        assertThat(searchEntity.getHashtags()[1]).isEqualTo("hash2");

    }

    @Test
    public void 등록하기(){

        String hashtags = "#java#javascript#python";

        PostDTO postDTO = PostDTO.builder()
                .id(1L)
                .accountId(1L)
                .contents("testing test!")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .hashtag(hashtags)
                .build();

        SearchEntity save = searchService.save(postDTO);

        assertThat(save.getId()).isEqualTo(1L);

    }

    @Test
    public void 삭제처리하기(){
        /*
            id기반으로 모든것을 덮어씌움!
         */
        String hashtags = "#java#javascript#python";

        PostDTO postDTO = PostDTO.builder()
                .id(1L)
                .accountId(1L)
                .contents("testing test!")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .hashtag(hashtags)
                .build();

        SearchEntity save = searchService.save(postDTO);

        PostDTO postDTO2 = PostDTO.builder()
                .id(1L)
                .accountId(1L)
                .contents("testing test!")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .hashtag(hashtags)
                .removed(true)
                .build();
        SearchEntity save2 = searchService.save(postDTO2);

        assertThat(save2.getId()).isEqualTo(1L);
        assertThat(save2.isRemoved()).isEqualTo(true);

    }

    @Test
    public void hashtag로가져오기() throws Exception{

        List<SearchEntity> list = searchService.searchByHashtag("java");
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(list));

    }

}
