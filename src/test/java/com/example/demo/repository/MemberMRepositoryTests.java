package com.example.demo.repository;


import com.example.demo.entity.MemberM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MemberMRepositoryTests {

    @Autowired
    private MemberMRepository memberMRepository;

    @BeforeEach
    public void 멤버추가() {
        MemberM memberM = MemberM.builder().name("hojong")
                .password("hojong").build();
        memberMRepository.save(memberM);
    }

    @Test
    public void 이름으로_조회() {
        //when
        MemberM hojong = memberMRepository.findOneByName("hojong").orElseThrow(RuntimeException::new);

        //then
        assertEquals("hojong", hojong.getName());
        assertEquals("hojong", hojong.getPassword());
        assertEquals("ROLE_USER", hojong.getAuthority());
    }
}
