package com.example.demo.service;


import com.example.demo.common.code.VacationTypeCode;
import com.example.demo.dto.VacationRequestDto;
import com.example.demo.repository.MemberMRepository;
import com.example.demo.repository.MemberVacationHistoryRepository;
import com.example.demo.repository.MemberVacationMRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;


@ExtendWith(SpringExtension.class)
public class VacationServiceTests {
    @InjectMocks
    private VacationService vacationService;

    @Mock
    private MemberMRepository memberMRepository;

    @Mock
    private MemberVacationMRepository vacationMRepository;

    @Mock
    private MemberVacationHistoryRepository historyRepository;

    @Test
    public void 휴가신청() {
        // given
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
