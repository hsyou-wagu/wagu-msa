package com.hsyou.wagusearch.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostDTO {
    private long id;
    private String contents;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime created;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updated;
    private boolean removed = false;
    private String hashtag;
    private long accountId;
    private List<CommentDTO> comments;

    public SearchEntity toSearchEntity(){

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            SearchEntity searchEntity = SearchEntity.builder()
                    .id(id)
                    .contents(contents)
                    .created(created.format(formatter))
                    .updated(updated.format(formatter))
                    .removed(removed)
                    .accountId(accountId)
                    .build();

            if (!hashtag.isEmpty()) {
                String[] rstHastags = Arrays.stream(hashtag.split("#")).filter(x -> !x.isEmpty()).toArray(String[]::new);
                searchEntity.setHashtags(rstHastags);
            }

            return searchEntity;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
