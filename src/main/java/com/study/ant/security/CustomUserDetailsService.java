package com.study.ant.security;

import com.study.ant.domain.Authority;
import com.study.ant.domain.Member;
import com.study.ant.sample.reposiroty.MemberRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository repository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = repository.findByUsername(username);

        if (member == null) {
            throw new UsernameNotFoundException(username);  //
        }

        return new User(member.getUsername(), member.getPassword(), getAuthorities(member.getAuthority()));
    }

    private List<GrantedAuthority> getAuthorities(List<Authority> list) throws Exception {
        List<GrantedAuthority> authorities = new ArrayList<>();

        /*
         UserDetails의 권한의 타입 : Collection<? extends GrantedAuthority>
         GrantedAuthority는 인터페이스
         SimpleGrantedAuthority 사용
         */
        for (Authority authority : list) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return authorities;
    }
}
