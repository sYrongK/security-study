package com.study.ant.security;

import com.study.ant.domain.Authority;
import com.study.ant.domain.Member;
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
//@DataJpaTest    // 자동으로 embeddedDatabase를 사용. @Transactional 무조건 rollback
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    //  기본 database를 그대로 사용하도록 Replace.NONE
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

        mvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

//    @Test
//    @DisplayName("로그인_시도")
//    @Transactional
//    void login() throws Exception {
//
//        Member admin = createAdmin("admin", "admin");
//
//        mvc.perform(SecurityMockMvcRequestBuilders.formLogin().user(admin.getUsername()).password(admin.getPassword()))
//                .andExpect(authenticated());
//
//        /*
//        authenticated() : 시큐리티 통해서 로그인에 성공하면, 해당 테스트 코드 통과 처리
//        unauthenticated() : 시큐리티 통해서 로그인 실패 케이스일 경우를 에상할 때 사용
//         */
//    }
}