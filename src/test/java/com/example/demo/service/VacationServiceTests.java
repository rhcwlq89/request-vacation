package com.example.demo.service;


import com.example.demo.common.code.VacationTypeCode;
import com.example.demo.dto.MemberDto;
import com.example.demo.dto.VacationDto;
import com.example.demo.dto.VacationRequestDto;
import com.example.demo.entity.MemberM;
import com.example.demo.entity.MemberVacationM;
import com.example.demo.repository.MemberMRepository;
import com.example.demo.repository.MemberVacationHistoryRepository;
import com.example.demo.repository.MemberVacationMRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
public class VacationServiceTests {
    @InjectMocks
    private VacationService vacationService;

    @Autowired
    private MemberService memberService;

    @Mock
    private MemberMRepository memberMRepository;

    @Mock
    private MemberVacationMRepository vacationMRepository;

    @Mock
    private MemberVacationHistoryRepository historyRepository;


    @Test
    public void 휴가신청() {
        // given
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        VacationRequestDto daysVacationDto = createDaysVacationDto();
        MemberDto memberDto = createMemberDto();
        MemberM memberM = createMemberM(memberDto, passwordEncoder);
        MemberVacationM vacationM = createVacationM(memberM);
        
        given(historyRepository.existsHistoryByVacationRequestDto(any(), any())).willReturn(false);
        given(memberMRepository.findOneByName(any())).willReturn(Optional.ofNullable(memberM));
        given(vacationMRepository.findByMemberM(any())).willReturn(Optional.ofNullable(vacationM));

        // when
        VacationDto vacationDto = vacationService.requestVacation(daysVacationDto);

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

    private MemberVacationM createVacationM(MemberM memberM) {
        MemberVacationM vacationM = new MemberVacationM();
        vacationM.setMemberM(memberM);
        vacationM.setTotalCount(BigDecimal.ONE);
        vacationM.setUseCount(BigDecimal.ZERO);
        vacationM.setMemberVacationId(1L);
        vacationM.setVacationYear(String.valueOf(LocalDate.now().getYear()));

        return vacationM;
    }

    private VacationRequestDto createDaysVacationDto() {
        return VacationRequestDto.builder().startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2))
                .typeCode(VacationTypeCode.DAYS)
                .memo("days").build();
    }

    private VacationRequestDto createHalfDayVacationDto() {
        return VacationRequestDto.builder().startDate(LocalDate.now())
                .typeCode(VacationTypeCode.HALF)
                .memo("half").build();
    }

    private VacationRequestDto createQuarterDayVacationDto() {
        return VacationRequestDto.builder().startDate(LocalDate.now())
                .typeCode(VacationTypeCode.QUARTER)
                .memo("quarter").build();
    }

}
