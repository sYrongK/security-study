package com.study.ant.sample.controller;

import com.study.ant.sample.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SampleController {

    @GetMapping("/test")
    public void test() throws Exception {
        /*
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         : @AuthenticationPrincipal
        */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("테스트");
    }

    @PostMapping("/admin")
    public void adminPage() throws Exception {
        /*
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         : @AuthenticationPrincipal
        */
        log.info("테스트");
    }

    @GetMapping("/home")
    public void home() throws Exception {
        //  ResponseEntity로 하면 보이지않을까
        log.info("home 화면으로 이동");
    }
}
