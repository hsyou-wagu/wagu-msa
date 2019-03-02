package com.hsyou.wagugateway.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDTO {
    private long userId;
    private String userEmail;
    private long id;
    private String name;
    private String email;
    private String img;
    private String uid;
    private boolean firstLogin = false;
    private ExternalAuthProvider provider;
}
