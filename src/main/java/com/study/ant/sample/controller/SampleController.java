package com.study.ant.sample.controller;

import com.study.ant.sample.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SampleController {

    @GetMapping("/test")
    public void test(@AuthenticationPrincipal MemberDto dto) throws Exception {
        /*
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         : @AuthenticationPrincipal
        */
        log.info("테스트");
    }

    @PostMapping("/admin")
    public void adminPage(@AuthenticationPrincipal MemberDto dto) throws Exception {
        /*
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         : @AuthenticationPrincipal
        */
        log.info("테스트");
    }

    @PostMapping("/test2")
    @PreAuthorize("hasRole('ADMIN')")
    public void test2(@AuthenticationPrincipal MemberDto dto) throws Exception {
        /*
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         : @AuthenticationPrincipal
        */
        log.info("테스트");
    }
}
