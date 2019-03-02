package com.hsyou.wagucomment.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {
    private long id;
    private String contents;
    private LocalDateTime created;
    private LocalDateTime updated;
    private boolean removed = false;
    private long userId;
    private long postId;

    public Comment toEntity(){
        return Comment.builder()
                .id(id)
                .contents(contents)
                .created(created)
                .updated(updated)
                .removed(removed)
                .userId(userId)
                .postId(postId)
                .build();
    }

}
