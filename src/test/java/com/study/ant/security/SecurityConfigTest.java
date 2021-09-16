package com.study.ant.security;

import com.study.ant.sample.reposiroty.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc   // @SpringBootTest와 함께 사용. @WebMvcText + MVC 테스트. AOP Jpa Repository 등 모든 설정을 같이 올려서 테스트 가능
class SecurityConfigTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("익명_접근")
//    @WithAnonymousUser
    void anonymous() throws Exception {

        mvc.perform(get("/test").with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("login_접근")
    void anonymous_login() throws Exception {

        mvc.perform(get("/login").with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("user_권한_테스트1")
    void access1() throws Exception {

        mvc.perform(get("/test").with(user("user1").roles("USER")))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("user_권한_테스트2")
    @WithMockUser(username = "user1", roles = "USER")
    void access2() throws Exception {

        mvc.perform(get("/admin_dto"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}