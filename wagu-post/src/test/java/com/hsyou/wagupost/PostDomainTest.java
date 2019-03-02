package com.hsyou.wagupost;

import com.hsyou.wagupost.model.Post;
import com.hsyou.wagupost.model.PostDTO;
import com.hsyou.wagupost.repository.PostRepository;
import com.hsyou.wagupost.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostDomainTest {

    @Autowired
    private PostRepository postRepository;

    final long accountId = 2L;
    final String contents = "test";

    @Test
    public void 포스트생성하기(){
        //given
        Post post = Post.builder()
                .accountId(accountId)
                .contents(contents)
                .build();

        //when
        long id = postRepository.save(post).getId();
        Post rst = postRepository.findById(id).get();

        //then
        assertThat(rst.getAccountId()).isEqualTo(accountId);
        assertThat(rst.getContents()).isEqualTo(contents);
    }

    @Test
    public void 포스트_수정하기(){
        //given
        Post post = Post.builder()
                .accountId(accountId)
                .contents(contents)
                .build();

        long id = postRepository.save(post).getId();
        //when
        String newContents = "test2";
        Post rst = postRepository.findById(id).get();
        rst.updatePostContents(newContents, accountId);
        Post rst2 = postRepository.save(rst);
        //then
        assertThat(rst2.getAccountId()).isEqualTo(accountId);
        assertThat(rst2.getContents()).isEqualTo(newContents);
    }
    @Test
    public void 포스트_삭제하기(){
        //given
        Post post = Post.builder()
                .accountId(accountId)
                .contents(contents)
                .build();

        long id = postRepository.save(post).getId();
        //when
        Post rst = postRepository.findById(id).get();
        rst.removePost(accountId);
        Post rst2 = postRepository.save(rst);
        //then
        assertThat(rst2.getAccountId()).isEqualTo(accountId);
        assertThat(rst2.isRemoved()).isEqualTo(true);
    }

}
