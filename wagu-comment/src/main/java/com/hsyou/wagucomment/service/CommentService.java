package com.hsyou.wagucomment.service;

import com.hsyou.wagucomment.model.Comment;
import com.hsyou.wagucomment.model.CommentDTO;
import com.hsyou.wagucomment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;


    public CommentDTO saveComment(Comment comment){
        return commentRepository.save(comment).toDTO();
    }

    public List<CommentDTO> listAllComment(long postId){
        return commentRepository.findByPostIdAndRemovedIsFalse(postId)
                .stream()
                .map(Comment::toDTO)
                .collect(Collectors.toList());

    }

    public CommentDTO removeComment(long id,long accountId){
        Optional<Comment> optComment = commentRepository.findById(id);

        if(optComment.isPresent()){
            if(optComment.get().getId() == accountId){

                optComment.get().setRemoved(true);

            }else{

                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not a Writer.");
            }
            return commentRepository.save(optComment.get()).toDTO();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment Not Found.");
        }
    }
}
