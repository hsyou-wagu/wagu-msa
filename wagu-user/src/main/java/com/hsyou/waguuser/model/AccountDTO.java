package com.hsyou.waguuser.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDTO {
    private String userId;
    private String userEmail;
    private long id;
    private String name;
    private String email;
    private String img;
    private String uid;
    private ExternalAuthProvider provider;
    private boolean firstLogin = false;

    public Account toEntity(){
        return Account.builder()
                .id(id)
                .name(name)
                .email(email)
                .img(img)
                .provider(provider)
                .uid(uid)
                .build();
    }

}
