package com.hsyou.wagucomment.model;

import lombok.*;
import org.apache.commons.lang.NullArgumentException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 255, nullable = false)
    private String contents;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
    @Column(nullable = false)
    private boolean removed = false;

    private long userId;
    private long postId;

    public CommentDTO toDTO(){
        return CommentDTO.builder()
                .id(id)
                .contents(contents)
                .created(created)
                .updated(updated)
                .removed(removed)
                .userId(userId)
                .postId(postId)
                .build();
    }


    public static Comment createComment(CommentDTO commentDTO, long accountId){
        validateComment(commentDTO);
        return Comment.builder()
                .postId(commentDTO.getPostId())
                .contents(commentDTO.getContents())
                .userId(accountId)
                .build();

    }

    private static void validateComment(CommentDTO commentDTO){
        if(commentDTO.getPostId()==0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "post Id null");
        }
    }

}
