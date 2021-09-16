package com.study.ant.security;

import com.study.ant.sample.dto.MemberDto;
import com.study.ant.sample.service.SampleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;

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

    @Test
//    @WithMockUser(username = "admin", password = "admin", roles = { "ADMIN", "USER" })      // role : "ROLE_USER", authority: "USER" prefix 차이
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "user")   //  구현한 UserDetailsService를 통해 직접 구현한 방식 테스트가 필요할 경우 사용. value : 해당 사용자를 찾는다.
    @DisplayName("userDetailService_테스트")
    void testUserDetailsService() throws Exception {
        MemberDto user = service.sample();

        log.info("Authentication ::: 사용자={}, 권한={}", user.getUsername(), user.getAuthorities());
    }
}
