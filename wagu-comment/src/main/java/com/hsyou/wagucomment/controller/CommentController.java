package com.hsyou.wagucomment.controller;

import com.hsyou.wagucomment.model.Comment;
import com.hsyou.wagucomment.model.CommentDTO;
import com.hsyou.wagucomment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/")
    public CommentDTO saveComment(@RequestBody CommentDTO commentDTO, long accountId){

        Comment comment = Comment.createComment(commentDTO,accountId);
        return commentService.saveComment(comment);

    }

    @GetMapping("/{postId}")
    public List<CommentDTO> listAllComment(@PathVariable long postId){
        return commentService.listAllComment(postId);
    }

    @DeleteMapping("/{commentId}")
    public CommentDTO removeComment(@PathVariable long commentId, long accountId){
        return commentService.removeComment(commentId,accountId);
    }

}
