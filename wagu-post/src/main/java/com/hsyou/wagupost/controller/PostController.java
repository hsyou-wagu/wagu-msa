package com.hsyou.wagupost.controller;

import com.hsyou.wagupost.model.Post;
import com.hsyou.wagupost.model.PostDTO;
import com.hsyou.wagupost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {


    @Autowired
    private PostService postService;


    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable long id){

        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping("")
    public ResponseEntity<PostDTO> savePost(@RequestBody PostDTO post, @RequestParam long accountId){
        Post entity = post.toEntity();
        entity.setWriter(accountId);
        return ResponseEntity.ok(postService.savePost(entity).toDTO());

    }

    @PutMapping("")
    public ResponseEntity<Post> updatePost(@RequestBody PostDTO post, @RequestParam long accountId){

        return ResponseEntity.ok(postService.updatePost(post.toEntity(), accountId));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<PostDTO>> listPost(Pageable pageable){
        return ResponseEntity.ok(postService.listPost(pageable));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<PostDTO> removePost(@PathVariable long id, @RequestParam long accountId) {
        return ResponseEntity.ok(postService.removePost(id,accountId).toDTO());
    }
}
