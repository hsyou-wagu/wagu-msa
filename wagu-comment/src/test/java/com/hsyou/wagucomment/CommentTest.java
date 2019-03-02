package com.hsyou.wagucomment;

import com.hsyou.wagucomment.model.Comment;
import com.hsyou.wagucomment.model.CommentDTO;
import com.hsyou.wagucomment.repository.CommentRepository;
import com.hsyou.wagucomment.service.CommentService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;
    @After
    public void cleanup() {
        /**
         이후 테스트 코드에 영향을 끼치지 않기 위해
         테스트 메소드가 끝날때 마다 respository 전체 비우는 코드
         **/
        commentRepository.deleteAll();
    }


    @Test
    public void 코멘트저장(){
        CommentDTO comment = CommentDTO.builder()
                .contents("abc")
                .userId(1L)
                .postId(1L)
                .build();

        CommentDTO saveComment = commentService.saveComment(comment);

        assertThat(saveComment.getContents()).isEqualTo("abc");
        assertThat(saveComment.getPostId()).isEqualTo(1L);
        assertThat(saveComment.getUserId()).isEqualTo(1L);

    }

    @Test
    public void 포스트코멘트가져오기(){
        CommentDTO comment = CommentDTO.builder()
                .contents("abc")
                .userId(1L)
                .postId(1L)
                .build();

        commentService.saveComment(comment);

        List<CommentDTO> list = commentService.listAllComment(1L);
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getContents()).isEqualTo("abc");
        assertThat(list.get(0).getUserId()).isEqualTo(1L);
    }
    @Test
    public void 코멘트지우기(){
        CommentDTO comment = CommentDTO.builder()
                .contents("abc")
                .userId(1L)
                .postId(1L)
                .build();

        CommentDTO comment2 = CommentDTO.builder()
                .contents("qwe")
                .userId(1L)
                .postId(1L)
                .build();

        commentService.saveComment(comment);
        CommentDTO target = commentService.saveComment(comment2);

        //when
        commentService.removeComment(target.getId());


        List<CommentDTO> list = commentService.listAllComment(1L);
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getContents()).isEqualTo("abc");
        assertThat(list.get(0).getUserId()).isEqualTo(1L);



    }
}
