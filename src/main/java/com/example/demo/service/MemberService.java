package com.example.demo.service;

import com.example.demo.dto.MemberDto;
import com.example.demo.dto.TokenDto;
import com.example.demo.entity.MemberM;
import com.example.demo.entity.MemberVacationM;
import com.example.demo.jwt.JwtFilter;
import com.example.demo.jwt.TokenProvider;
import com.example.demo.repository.MemberMRepository;
import com.example.demo.repository.MemberVacationMRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {
    private final MemberMRepository memberMRepository;
    private final MemberVacationMRepository vacationMRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public Long signUp(MemberDto memberDto) {
        memberMRepository.findOneByName(memberDto.getName()).ifPresent(memberM -> {
            throw new RuntimeException("이미 가입한 유저입니다.");
        });

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        MemberM memberM = MemberM.builder()
                .name(memberDto.getName())
                .password(passwordEncoder.encode(memberDto.getPassword())).build();
        memberMRepository.save(memberM);

        MemberVacationM vacationM = new MemberVacationM();
        vacationM.setMemberM(memberM);
        vacationM.setTotalCount(BigDecimal.valueOf(15d));
        vacationM.setUseCount(BigDecimal.valueOf(0d));
        vacationM.setVacationYear(String.valueOf(LocalDate.now().getYear()));
        vacationMRepository.save(vacationM);

        return memberM.getId();
    }

    public TokenDto signIn(MemberDto memberDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberDto.getName(), memberDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new TokenDto(jwt);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberMRepository.findOneByName(username).map(user->createUser(user))
        .orElseThrow(()-> new RuntimeException(username + " 회원 정보가 없습니다."));
    }

    private User createUser(MemberM user) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getAuthority());
        return new User(user.getName(), user.getPassword(), List.of(simpleGrantedAuthority));
    }
}
