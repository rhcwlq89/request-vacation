package com.example.demo.service;

import com.example.demo.entity.MemberM;
import com.example.demo.repository.MemberMRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MemberService {
    private final MemberMRepository memberMRepository;

    public MemberService(MemberMRepository memberMRepository) {
        this.memberMRepository = memberMRepository;
    }

    public void signin() {
        List<MemberM> all = memberMRepository.findAll();
    }

    public void signup() {
        List<MemberM> all = memberMRepository.findAll();
    }


}
