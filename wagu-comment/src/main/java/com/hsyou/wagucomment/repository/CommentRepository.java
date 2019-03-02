package com.hsyou.wagucomment.repository;

import com.hsyou.wagucomment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostIdAndRemovedIsFalse(Long postId);
}
