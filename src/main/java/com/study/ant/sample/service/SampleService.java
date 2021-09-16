package com.study.ant.sample.service;

import com.study.ant.sample.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SampleService {

    public MemberDto sample() throws Exception {

        return (MemberDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
