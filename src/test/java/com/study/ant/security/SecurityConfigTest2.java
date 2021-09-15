package com.study.ant.security;

import com.study.ant.domain.Authority;
import com.study.ant.domain.Member;
import com.study.ant.sample.reposiroty.MemberRepository;
import com.study.ant.sample.service.SampleService;
import javassist.tools.rmi.Sample;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;

@DataJpaTest    // 자동으로 embeddedDatabase를 사용. @Transactional (무조건 rollback)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    //  연결하고자 하는 database 그대로 사용하도록 Replace.NONE
@ComponentScan(basePackages = { "com.study.ant.security", "com.study.ant.sample" })
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/test.sql")   // test 전 쿼리 실행  @BeforeEach가 안 먹혀..
@Slf4j
public class SecurityConfigTest2 {

    @Autowired
    private SampleService service;

    @Autowired
    private MemberRepository repository;

    @Test
//    @WithMockUser(username = "admin", password = "admin", roles = { "ADMIN", "USER" })      // role : "ROLE_USER", authority: "USER" prefix 차이
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "user2")   //  구현한 UserDetailsService를 통해 직접 구현한 방식 테스트가 필요할 경우 사용. value : 해당 사용자를 찾는다.
    void testUserDetailsService() throws Exception {
        User user = service.sample();
    }

//    /**
//     * admin 추가
//     */
//    private Member createAdmin (String id, String pwd) throws Exception {
//        Authority adminAuth = new Authority();
//        adminAuth.setAuthority("ADMIN");
//        Authority userAuth = new Authority();
//        userAuth.setAuthority("USER");
//
//        Member admin = new Member();
//        admin.setUsername(id);
//        admin.setPassword(pwd);
//        admin.setAuthority(Arrays.asList(adminAuth, userAuth));
//
//        repository.save(admin);
//        return admin;
//    }
//
//    /**
//     * user 가입
//     */
//    private Member join(String id, String pwd) throws Exception {
//        Authority authority = new Authority();
//        authority.setAuthority("USER");
//
//        Member user = new Member();
//        user.setUsername(id);
//        user.setPassword(pwd);
//        user.setAuthority(Arrays.asList(authority));
//
//        repository.save(user);
//        return user;
//    }
}
