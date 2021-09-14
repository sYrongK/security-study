package com.study.ant.member.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity(name = "Authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    @Column(name = "authority")
    private String authority;
}
