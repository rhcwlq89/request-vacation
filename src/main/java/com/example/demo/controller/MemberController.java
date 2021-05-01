package com.example.demo.controller;

import com.example.demo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<String> signin() {
        log.info("sign in");
        memberService.signin();
        return ResponseEntity.ok("Success");
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signup() {
        log.info("sign up");
        memberService.signup();
        return ResponseEntity.ok("Success");
    }
}
