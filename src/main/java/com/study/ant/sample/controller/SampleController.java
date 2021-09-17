package com.study.ant.sample.controller;

import com.study.ant.sample.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class SampleController {

    @GetMapping("/test")
    public ResponseEntity test() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Map<String, String> response = new HashMap<>();
        response.put("message", "test SUCCESS");
        response.put("authentication", String.valueOf(authentication.getPrincipal()));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin_dto")
    public ResponseEntity adminPageDto(@AuthenticationPrincipal MemberDto member) throws Exception {

        Map<String, String> response = new HashMap<>();
        response.put("message", "Custom User @AuthenticationPrincipal SUCCESS");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/main")
    public ResponseEntity main() throws Exception {

        Map<String, String> response = new HashMap<>();
        response.put("message", "Login Success. go to main");

        return ResponseEntity.ok(response);
    }
}
