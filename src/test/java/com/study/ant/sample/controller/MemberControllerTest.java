package com.study.ant.sample.controller;

import com.study.ant.domain.Authority;
import com.study.ant.domain.Member;
import com.study.ant.sample.reposiroty.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;


@SpringBootTest
@WebMvcTest(SampleController.class)
class MemberControllerTest {

    @MockBean
    private MockMvc mvc;

    @Autowired
    private MemberRepository repository;

    @BeforeEach
    void before() {

        Authority adminAuth = new Authority();
        adminAuth.setAuthority("ADMIN");
        Authority userAuth = new Authority();
        userAuth.setAuthority("USER");

        Member admin = new Member();
        admin.setUsername("admin");
        admin.setPassword("admin11");
        admin.setAuthority(Arrays.asList(adminAuth, userAuth));

        repository.save(admin);
    }

    @Test
    @DisplayName("시큐리티_관리자 테스트")
    void test() throws Exception {
//        시큐리티 테스트
//        mmvc.perform(get("/test")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(json))
//                .andExpect(status().isOk())
//                .andDo(print());    // 요청,응답 전체 메세지 확인

    }

}