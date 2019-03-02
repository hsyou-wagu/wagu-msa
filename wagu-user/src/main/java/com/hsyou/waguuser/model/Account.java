package com.hsyou.waguuser.model;

import lombok.*;

import javax.persistence.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @Column(nullable = false)
    private String email;
    private String img;

    private String uid;
    @Enumerated(EnumType.ORDINAL)
    private ExternalAuthProvider provider;


    public AccountDTO toDTO(){
        return AccountDTO.builder()
                .id(this.getId())
                .name(this.getName())
                .email(this.getEmail())
                .img(this.getImg())
                .uid(this.getUid())
                .build();
    }

}
