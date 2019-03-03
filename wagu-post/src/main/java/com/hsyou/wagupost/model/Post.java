package com.hsyou.wagupost.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime created;
    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updated;
    @Column(nullable = false)
    private boolean removed = false;
    private String hashtag;
    private long accountId;

    public PostDTO toDTO(){
        return PostDTO.builder()
                .id(id)
                .contents(contents)
                .created(created)
                .updated(updated)
                .removed(removed)
                .hashtag(hashtag)
                .accountId(accountId)
                .build();
    }
    public static Post createPost(PostDTO dto, long accountId){
        validatePost(dto);
        validateAccount(accountId);
        return Post.builder()
                .contents(dto.getContents())
                .hashtag(dto.getHashtag())
                .accountId(accountId)
                .build();
    }

    private static void validatePost(PostDTO postDTO){
        if(postDTO.getContents().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contents can't be null");
        }
    }
    private static void validateAccount(long accountId){
        if(accountId == 0){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User를 찾을 수 없습니다.");
        }
    }

    public void setWriter(long accountId){
        this.accountId = accountId;
    }

    private boolean isPostOwner(long accountId){
        return this.accountId == accountId;
    }

    public void updatePostContents(String newContents, long accountId){
        if(!isPostOwner(accountId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized, Not a Writer");
        }
        if(this.removed){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Removed Post");
        }
        this.contents = newContents;
    }

    public void removePost(long accountId){
        if(!isPostOwner(accountId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized, Not a Writer");
        }
        this.removed = true;
    }
}
