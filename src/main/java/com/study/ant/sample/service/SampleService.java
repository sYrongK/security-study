package com.study.ant.sample.service;

import com.study.ant.sample.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SampleService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User sample() throws Exception {

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
