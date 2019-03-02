package com.hsyou.wagupost;


import com.hsyou.wagupost.model.Post;
import com.hsyou.wagupost.service.PostService;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {
    @Autowired
    private PostService postService;

    final long accountId = 1L;
    final String contents = "test";
    final String hashtag = "#hashtag#wagu";
    @Test
    public void savePost(){
        Post post = Post.builder()
                .accountId(accountId)
                .contents(contents)
                .hashtag(hashtag)
                .build();

        Post rst = postService.savePost(post);

        assertThat(rst.getAccountId()).isEqualTo(accountId);
        assertThat(rst.getContents()).isEqualTo(contents);
        assertThat(rst.getHashtag()).isEqualTo(hashtag);
    }

    @Test
    public void updatePost(){
        //given
        Post post = Post.builder()
                .accountId(accountId)
                .contents(contents)
                .build();

        Post rst = postService.savePost(post);
        long id = rst.getId();
        //when
        String newContents = "test2";
        Post newPost = Post.builder()
                .id(id)
                .accountId(accountId)
                .contents(newContents)
                .build();

        Post rst2 = postService.updatePost(newPost, accountId);

        //then
        assertThat(rst2.getContents()).isEqualTo(newContents);
    }

    @Test
    public void removePost(){
        Post post = Post.builder()
                .accountId(accountId)
                .contents(contents)
                .build();
        Post rst = postService.savePost(post);
        long id = rst.getId();

        Post rst2 = postService.removePost(id,accountId);

        assertThat(rst2.isRemoved()).isEqualTo(true);
    }
}
