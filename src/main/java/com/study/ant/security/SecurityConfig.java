package com.study.ant.security;

import com.study.ant.security.handler.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//  STEP1 nothing
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider authenticationProvider;

    private final CustomUserDetailsService userDetailsService;

    /**
     * SpringSecurityFilterChain에 대한 설정
     * 리소스 보안을 위한 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * antMatchers
         * hasRole, hasAuthority
         * csrf
         */

        http
                .authorizeRequests()    //  HttpServletRequest를 사용하는 요청들에대한 접근 제한을 설정하겠다
                .antMatchers("/login/**","/h2-console/**").permitAll()
                .antMatchers("/admin").hasRole("ADMIN") // request에 권한 제한 두기
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login/process")
                .defaultSuccessUrl("/login/success")    //로그인 성공 후 url
                .successHandler(new CustomLoginSuccessHandler())
                .and()
                .logout()
                .invalidateHttpSession(true)    // session 무효화. true설정하면 sessionManagement 활성화
                .and()
                .csrf().disable()   // csrf:
                .sessionManagement()
                .invalidSessionUrl("/login")    // 세션 지워버리면 여기로 가나?
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
        auth.authenticationProvider(authenticationProvider) // this.authenticationProviders.add(authenticationProvider); 의미가..?
                .userDetailsService(userDetailsService);
    }

}
