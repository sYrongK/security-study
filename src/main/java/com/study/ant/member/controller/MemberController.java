package com.study.ant.member.controller;

import com.study.ant.member.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MemberController {

    @GetMapping("/test")
    public void test(@AuthenticationPrincipal MemberDto dto) throws Exception {
        /*
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         : @AuthenticationPrincipal
        */
        log.info("테스트");
    }
}
