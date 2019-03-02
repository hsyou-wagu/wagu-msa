package com.hsyou.wagupost.service;

import com.hsyou.wagupost.event.PostEvent;
import com.hsyou.wagupost.model.CommentDTO;
import com.hsyou.wagupost.model.Post;
import com.hsyou.wagupost.model.PostDTO;
import com.hsyou.wagupost.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/*
    Business logic 처리
 */
@Service
@Transactional
public class PostService implements ApplicationEventPublisherAware{

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentService commentService;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public Post savePost(Post post){
        try {
            Post rst = postRepository.save(post);
            eventPublisher.publishEvent(new PostEvent(rst.toDTO()));

            return rst;
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DB Error");
        }
    }

    public Post updatePost(Post newPost, long accountId){
        Optional<Post> optPost = postRepository.findById(newPost.getId());
        if(!optPost.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post Not Found.");
        }
        newPost.updatePostContents(newPost.getContents(), accountId);

        return postRepository.save(newPost);
    }

    public Page<PostDTO> listPost (Pageable pageable){
        return postRepository.findAll(pageable).map(p -> p.toDTO());
    }

    public PostDTO getPost(long id){
        Optional<Post> optPost = postRepository.findById(id);
        if(!optPost.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post Not Found.");
        }
        PostDTO postDTO = optPost.get().toDTO();
        List<CommentDTO> comments = commentService.requestComment(id);
        comments.stream().forEach(x -> System.out.println(x.getContents()));
        postDTO.setComments(comments);
        return postDTO;
    }

    public Post removePost(long postId, long accountId){

        Optional<Post> optPost = postRepository.findById(postId);
        if(!optPost.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post Not Found.");
        }

        Post post = optPost.get();
        post.removePost(accountId);

        eventPublisher.publishEvent(new PostEvent(post.toDTO()));

        return postRepository.save(post);

    }
}
