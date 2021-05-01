package com.example.demo.service;

import com.example.demo.dto.MemberDto;
import com.example.demo.entity.MemberM;
import com.example.demo.entity.MemberVacationM;
import com.example.demo.repository.MemberMRepository;
import com.example.demo.repository.MemberVacationMRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {
    private final MemberMRepository memberMRepository;
    private final MemberVacationMRepository vacationMRepository;

    @Transactional
    public void signup(MemberDto memberDto) {
        memberMRepository.findOneByName(memberDto.getName()).ifPresent(memberM -> {
            throw new RuntimeException("이미 가입한 유저입니다.");
        });

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        MemberM memberM = new MemberM();
        memberM.setName(memberDto.getName());
        memberM.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        memberMRepository.save(memberM).getId();

        // 사용자에게는 매년 15일의 연차가 부여됩니다
        MemberVacationM vacationM = new MemberVacationM();
        vacationM.setMemberM(memberM);
        vacationM.setTotalCount(15d);
        vacationM.setUseCount(0d);
        vacationM.setVacationYear(String.valueOf(LocalDate.now().getYear()));
        vacationMRepository.save(vacationM);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberMRepository.findOneByName(username).map(user->createUser(username, user))
        .orElseThrow(()-> new RuntimeException(username + " 회원 정보가 없습니다."));
    }

    private User createUser(String username, MemberM user) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getAuthority());
        return new User(user.getName(), user.getPassword(), List.of(simpleGrantedAuthority));
    }
}
