package com.example.demo.repository;


import com.example.demo.dto.VacationDto;
import com.example.demo.entity.MemberM;
import com.example.demo.entity.MemberVacationM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MemberVacationMRepositoryTests {
    @Autowired
    private MemberVacationMRepository vacationMRepository;
    @Autowired
    private MemberMRepository memberMRepository;

    @BeforeEach
    public void init() {
        MemberM memberM = MemberM.builder().name("hojong")
                .password("hojong").build();
        memberMRepository.save(memberM);

        MemberVacationM vacationM = MemberVacationM.builder().memberM(memberM)
                .totalCount(BigDecimal.valueOf(15L)).useCount(BigDecimal.valueOf(0L))
                .vacationYear("2021").build();
        vacationMRepository.save(vacationM);
    }

    @Test
    public void 휴가정보조회_회원() {
        // when
        MemberM memberM = memberMRepository.findOneByName("hojong").orElseThrow(RuntimeException::new);
        MemberVacationM vacationM = vacationMRepository.findByMemberM(memberM).orElseThrow(RuntimeException::new);

        // then
        assertEquals(memberM.getId(), vacationM.getMemberM().getId());
        assertEquals(15L, vacationM.getTotalCount().longValue());
        assertEquals(0L, vacationM.getUseCount().longValue());
        assertEquals("2021", vacationM.getVacationYear());
    }

    @Test
    public void 휴가정보조회_회원명_연도() {
        // when
        VacationDto hojong = vacationMRepository.findVacationByMemberNameAndVacationYear("hojong", "2021");

        assertEquals("hojong", hojong.getName());
        assertEquals(15L, hojong.getTotalCount().longValue());
        assertEquals(0L, hojong.getUseCount().longValue());
        assertEquals("2021", hojong.getVacationYear());
    }

}
