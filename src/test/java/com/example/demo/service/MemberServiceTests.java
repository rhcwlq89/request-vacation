package com.example.demo.service;


import com.example.demo.dto.MemberDto;
import com.example.demo.entity.MemberM;
import com.example.demo.repository.MemberMRepository;
import com.example.demo.repository.MemberVacationMRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class MemberServiceTests {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberMRepository memberMRepository;

    @Mock
    private MemberVacationMRepository memberVacationMRepository;

    @Test
    public void 회원가입() {
        // given
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        MemberDto memberDto = createMemberDto();
        MemberM memberM = createMemberM(memberDto, passwordEncoder);

        given(memberMRepository.save(any())).willReturn(memberM);
        given(memberMRepository.findById(any())).willReturn(Optional.ofNullable(memberM));

        // when
        Long id = memberService.signUp(memberDto);

        // then
        MemberM result = memberMRepository.findById(id).get();

        assertEquals(id, result.getId());
    }

    @Test
    public void 로그인() {
        // given
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        MemberDto memberDto = createMemberDto();
        MemberM memberM = createMemberM(memberDto, passwordEncoder);

        given(memberMRepository.findOneByName(any())).willReturn(Optional.ofNullable(memberM));

        // when
        UserDetails userDetails = memberService.loadUserByUsername(memberDto.getName());

        // then
        assertEquals(memberM.getName(), userDetails.getUsername());
        assertEquals(memberM.getPassword(), userDetails.getPassword());
    }


    private MemberM createMemberM(MemberDto memberDto, BCryptPasswordEncoder passwordEncoder) {
        return MemberM.builder()
                .id(1l)
                .name(memberDto.getName())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .authority("ROLE_USER")
                .build();
    }

    private MemberDto createMemberDto() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("hojong");
        memberDto.setPassword("hojong");
        return memberDto;
    }


}