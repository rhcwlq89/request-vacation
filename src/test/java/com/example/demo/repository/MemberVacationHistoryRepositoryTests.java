package com.example.demo.repository;


import com.example.demo.common.code.VacationStatusCode;
import com.example.demo.common.code.VacationTypeCode;
import com.example.demo.dto.VacationHistoryDto;
import com.example.demo.dto.VacationRequestDto;
import com.example.demo.entity.MemberM;
import com.example.demo.entity.MemberVacationHistory;
import com.example.demo.entity.MemberVacationM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MemberVacationHistoryRepositoryTests {

    @Autowired
    private MemberVacationHistoryRepository historyRepository;
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

        MemberVacationHistory history = MemberVacationHistory.builder()
                .memberVacationM(vacationM)
                .startDate(LocalDate.parse("2021-05-10"))
                .endDate(LocalDate.parse("2021-05-10"))
                .requestStatus(VacationStatusCode.BEFORE.name())
                .vacationDays(BigDecimal.ONE)
                .memo("memo")
                .regDt(LocalDateTime.now())
                .modDt(LocalDateTime.now())
                .build();
        historyRepository.save(history);

        MemberVacationHistory usedHistory = MemberVacationHistory.builder()
                .memberVacationM(vacationM)
                .startDate(LocalDate.parse("2021-05-10"))
                .endDate(LocalDate.parse("2021-05-10"))
                .requestStatus(VacationStatusCode.USED.name())
                .vacationDays(BigDecimal.ONE)
                .memo("memo")
                .regDt(LocalDateTime.now())
                .modDt(LocalDateTime.now())
                .build();
        historyRepository.save(usedHistory);

        MemberVacationHistory cancelHistory = MemberVacationHistory.builder()
                .memberVacationM(vacationM)
                .startDate(LocalDate.parse("2021-05-10"))
                .endDate(LocalDate.parse("2021-05-10"))
                .requestStatus(VacationStatusCode.CANCEL.name())
                .vacationDays(BigDecimal.ONE)
                .memo("memo")
                .regDt(LocalDateTime.now())
                .modDt(LocalDateTime.now())
                .build();
        historyRepository.save(cancelHistory);
    }

    @Test
    public void 휴가히스토리조회_이름_연도() {
        // when
        List<VacationHistoryDto> historyDtos = historyRepository.findByNameAndYear("hojong", "2021");

        // then
        assertEquals(3, historyDtos.size());
    }

    @Test
    public void 휴가히스토리조회_상태_날짜() {
        // when
        List<MemberVacationHistory> before = historyRepository
                .findHistoryListByStatusCode(VacationStatusCode.BEFORE, LocalDate.parse("2021-05-10"));

        // then
        assertEquals(1, before.size());
        assertEquals(VacationStatusCode.BEFORE.name(), before.get(0).getRequestStatus());

        // when
        List<MemberVacationHistory> used = historyRepository
                .findHistoryListByStatusCode(VacationStatusCode.USED, LocalDate.parse("2021-05-10"));
        // then
        assertEquals(1, used.size());
        assertEquals(VacationStatusCode.USED.name(), used.get(0).getRequestStatus());

        // when
        List<MemberVacationHistory> cancel = historyRepository
                .findHistoryListByStatusCode(VacationStatusCode.CANCEL, LocalDate.parse("2021-05-10"));
        // then
        assertEquals(1, cancel.size());
        assertEquals(VacationStatusCode.CANCEL.name(), cancel.get(0).getRequestStatus());
    }

    @Test
    public void 중복휴가() {
        // when
        VacationRequestDto daysVacationRequestDto = createDaysVacationDto();
        boolean dayResult = historyRepository
                .existsHistoryByVacationRequestDto(daysVacationRequestDto, "hojong");
        // then
        assertFalse(dayResult);

        // when
        VacationRequestDto halfDayVacationRequestDto = createHalfDayVacationDto();
        boolean halfResult = historyRepository
                .existsHistoryByVacationRequestDto(halfDayVacationRequestDto, "hojong");
        // then
        assertTrue(halfResult);

        // when
        VacationRequestDto quarterDayVacationDto = createQuarterDayVacationDto();
        boolean quarterResult = historyRepository
                .existsHistoryByVacationRequestDto(quarterDayVacationDto, "hojong");
        // then
        assertTrue(quarterResult);
    }

    @Test
    public void 취소가능한_휴가조회() {
        List<MemberVacationHistory> all = historyRepository.findAll();
        all.forEach(memberVacationHistory -> {
            // when
            historyRepository
                    .findByHistoryIdAndRequestStatus(memberVacationHistory.getHistoryId(), VacationStatusCode.BEFORE.name(), "hojong")
                    .ifPresent(history-> {
                        assertEquals(memberVacationHistory.getHistoryId(), history.getHistoryId());
                        assertEquals(VacationStatusCode.BEFORE.name(), history.getRequestStatus());
                    });

        });
    }



    private VacationRequestDto createDaysVacationDto() {
        return VacationRequestDto.builder().startDate(LocalDate.parse("2021-05-11"))
                .endDate(LocalDate.parse("2021-05-11").plusDays(7))
                .typeCode(VacationTypeCode.DAYS)
                .memo("days").build();
    }

    private VacationRequestDto createHalfDayVacationDto() {
        return VacationRequestDto.builder().startDate(LocalDate.parse("2021-05-10"))
                .typeCode(VacationTypeCode.HALF)
                .memo("half").build();
    }

    private VacationRequestDto createQuarterDayVacationDto() {
        return VacationRequestDto.builder().startDate(LocalDate.parse("2021-05-10"))
                .typeCode(VacationTypeCode.QUARTER)
                .memo("quarter").build();
    }

}
