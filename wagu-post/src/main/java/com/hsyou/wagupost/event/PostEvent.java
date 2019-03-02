package com.hsyou.wagupost.event;

import com.hsyou.wagupost.model.PostDTO;
import lombok.Getter;

@Getter
public class PostEvent {
    private PostDTO postDTO;

    public PostEvent(PostDTO postDTO) {
        this.postDTO = postDTO;
    }
}
