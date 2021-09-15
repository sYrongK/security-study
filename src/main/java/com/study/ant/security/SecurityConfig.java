package com.study.ant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//  STEP1 nothing
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * SpringSecurityFilterChain에 대한 설정
     * 리소스 보안을 위한 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()    //  HttpServletRequest를 사용하는 요청들에대한 접근 제한을 설정하겠다
                .antMatchers("/login/**","/h2-console/**").permitAll()
                .antMatchers("/admin").hasRole("ADMIN") // request에 권한 제한 두기
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login/process")
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();    //  h2 웹 콘솔 설정에 필요
    }

    /**
     * Spring Security의 전역 설정
     * HttpSecurity보다 우선시 되며, 정적 자원에 대해선 Security Filter가 적용되지 않도록 설정 가능
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/static/**", "/resources/**");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
        사용자 세부 서비스 설정   ??
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

//        auth.authenticationProvider()
        auth.authenticationProvider(authenticationProvider)
                .userDetailsService(userDetailsService);
    }

}
