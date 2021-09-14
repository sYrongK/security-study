package com.study.ant.member.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Member")
@EqualsAndHashCode
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    @EqualsAndHashCode.Include
    private Integer idx;

    @Column(name = "id")
    private String id;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "member", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Authority> authority;
}
