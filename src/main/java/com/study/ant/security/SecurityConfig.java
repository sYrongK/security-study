package com.study.ant.security;

import com.study.ant.security.handler.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//  STEP1 nothing
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider defaultProvider;
    private final CustomUserDetailsAuthenticationProvider userDetailsProvider;

    private final CustomUserDetailsService userDetailsService;

    /**
     * SpringSecurityFilterChain에 대한 설정
     * 리소스 보안을 위한 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()    //  HttpServletRequest를 사용하는 요청들에대한 접근 제한을 설정하겠다
                .antMatchers("/login/**", "/h2-console/**").permitAll()  //  ant pattern
                .antMatchers("/admin_dto").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                /*로그인*/
                .formLogin()
                .loginProcessingUrl("/login/process")
//                .defaultSuccessUrl("/main")    //로그인 성공 후 url
                .successHandler(new CustomLoginSuccessHandler())
                .failureHandler(new CustomLoginFailureHandler())
                .and()
                /*로그아웃*/
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)    // session 무효화. true설정하면 sessionManagement 활성화
//                .deleteCookies()
//                .logoutSuccessHandler(new CustomLogoutHandler())
                .and()
                /*예외 처리*/
//                .exceptionHandling()
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증 실패시
//                .accessDeniedHandler(new CustomAccessDeniedHandler())           //  인가 실패시
//                .and()
                /*csrf*/
                .csrf().disable()   // csrf: 사용자 의도와 다른 요청을 서버에 넣게 하는 공격
                /*세션*/
                .sessionManagement()
                .invalidSessionUrl("/")             // 세션이 유효하지 않을 때 이동할 url
                    .maximumSessions(1)             //  최대 세션 갯수
                    .maxSessionsPreventsLogin(true) //  동시 로그인 차단. false : 기존 세션 만료
                    .expiredUrl("/")                //  세션 만료시 이동 페이지
                    .and()
                .and()
                .headers().frameOptions().disable();    //  h2 웹 콘솔 설정에 필요
    }

    /**
     * Spring Security의 전역 설정
     * HttpSecurity보다 우선시된다.
     * 특정 요청에 대해서는 시큐리티 보안 설정을 무시하도록 설정 즉, 보안예외처리(정적 리소스 무시 여부, 디버그 모드 설정, 사용자 지정 방화벽 정의 등)
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/static/**", "/resources/**");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
        사용자 세부 서비스 설정
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
        인메모리
        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN, USER")
                .and()
                .withUser("user1").password("1234").roles("USER");
                */
        auth
//                .authenticationProvider(defaultProvider)    // defaultProvider  userDetailsProvider
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());  // provider 구현 안하고 userDetailsService만 탈 경우 꼭 여기서 필요
    }

}
