package com.example.demo.controller;

import com.example.demo.common.ResponseMessage;
import com.example.demo.dto.MemberDto;
import com.example.demo.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@AllArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;


    @PostMapping(path = "/signin")
    public ResponseEntity<ResponseMessage> signin(@RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(new ResponseMessage(memberService.signIn(memberDto), "success"));
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<ResponseMessage> signup(@RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(new ResponseMessage(memberService.signUp(memberDto), "success"));
    }
}
