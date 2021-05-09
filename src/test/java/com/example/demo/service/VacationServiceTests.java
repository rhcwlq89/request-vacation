package com.example.demo.service;


import com.example.demo.common.code.VacationStatusCode;
import com.example.demo.common.code.VacationTypeCode;
import com.example.demo.dto.MemberDto;
import com.example.demo.dto.VacationDto;
import com.example.demo.dto.VacationHistoryDto;
import com.example.demo.dto.VacationRequestDto;
import com.example.demo.entity.MemberM;
import com.example.demo.entity.MemberVacationHistory;
import com.example.demo.entity.MemberVacationM;
import com.example.demo.repository.MemberMRepository;
import com.example.demo.repository.MemberVacationHistoryRepository;
import com.example.demo.repository.MemberVacationMRepository;
import com.example.demo.util.SecurityUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


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
    private static MockedStatic<SecurityUtil> utilities;

    @BeforeAll
    public static void init() {
        utilities = Mockito.mockStatic(SecurityUtil.class);
    }

    @AfterAll
    public static void close() {
        utilities.close();
    }

    @Test
    public void 휴가신청_정상() {
        // given
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        VacationRequestDto daysVacationDto = createDaysVacationDto();
        VacationRequestDto halfDayVacationDto = createHalfDayVacationDto();
        VacationRequestDto quarterDayVacationDto = createQuarterDayVacationDto();

        MemberDto memberDto = createMemberDto();
        MemberM memberM = createMemberM(memberDto, passwordEncoder);
        MemberVacationM vacationM = createVacationM(memberM);

        utilities.when(SecurityUtil::getCurrentUsername).thenReturn(Optional.ofNullable("hojong"));
        given(historyRepository.existsHistoryByVacationRequestDto(any(), any())).willReturn(false);
        given(memberMRepository.findOneByName(any())).willReturn(Optional.ofNullable(memberM));
        given(vacationMRepository.findByMemberM(any())).willReturn(Optional.ofNullable(vacationM));

        // when
        VacationDto dayResult = vacationService.requestVacation(daysVacationDto);
        VacationDto halfResult = vacationService.requestVacation(halfDayVacationDto);
        VacationDto quarterResult = vacationService.requestVacation(quarterDayVacationDto);

        // then
        assertEquals(daysVacationDto.getStartDate().getYear(), Integer.valueOf(dayResult.getVacationYear()));
        assertEquals(halfDayVacationDto.getStartDate().getYear(), Integer.valueOf(halfResult.getVacationYear()));
        assertEquals(quarterDayVacationDto.getStartDate().getYear(), Integer.valueOf(quarterResult.getVacationYear()));
    }

    @Test
    public void 휴가신청_미회원() {
        // given
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        VacationRequestDto daysVacationDto = createDaysVacationDto();
        MemberDto memberDto = createMemberDto();
        MemberM memberM = createMemberM(memberDto, passwordEncoder);
        MemberVacationM vacationM = createVacationM(memberM);

        utilities.when(SecurityUtil::getCurrentUsername).thenReturn(Optional.ofNullable(null));
        given(historyRepository.existsHistoryByVacationRequestDto(any(), any())).willReturn(false);
        given(memberMRepository.findOneByName(any())).willReturn(Optional.ofNullable(memberM));
        given(vacationMRepository.findByMemberM(any())).willReturn(Optional.ofNullable(vacationM));

        // when
        RuntimeException unexpectedUserException = assertThrows(RuntimeException.class, () -> {
            vacationService.requestVacation(daysVacationDto);
        });

        // then
        String message = unexpectedUserException.getMessage();
        assertEquals("알 수 없는 사용자입니다.", message);
    }

    @Test
    public void 휴가신청_중복휴가() {
        // given
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        VacationRequestDto daysVacationDto = createDaysVacationDto();
        MemberDto memberDto = createMemberDto();
        MemberM memberM = createMemberM(memberDto, passwordEncoder);
        MemberVacationM vacationM = createVacationM(memberM);

        utilities.when(SecurityUtil::getCurrentUsername).thenReturn(Optional.ofNullable("hojong"));
        given(historyRepository.existsHistoryByVacationRequestDto(any(), any())).willReturn(true);
        given(memberMRepository.findOneByName(any())).willReturn(Optional.ofNullable(memberM));
        given(vacationMRepository.findByMemberM(any())).willReturn(Optional.ofNullable(vacationM));

        // when
        RuntimeException unexpectedUserException = assertThrows(RuntimeException.class, () -> {
            vacationService.requestVacation(daysVacationDto);
        });

        // then
        String message = unexpectedUserException.getMessage();
        assertEquals("중복된 휴가가 있습니다.", message);
    }

    @Test
    public void 휴가신청_휴가기간오류() {
        // given
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        VacationRequestDto daysVacationDto = createDaysVacationDto();
        daysVacationDto.setEndDate(LocalDate.now());

        MemberDto memberDto = createMemberDto();
        MemberM memberM = createMemberM(memberDto, passwordEncoder);
        MemberVacationM vacationM = createVacationM(memberM);

        utilities.when(SecurityUtil::getCurrentUsername).thenReturn(Optional.ofNullable("hojong"));
        given(historyRepository.existsHistoryByVacationRequestDto(any(), any())).willReturn(false);
        given(memberMRepository.findOneByName(any())).willReturn(Optional.ofNullable(memberM));
        given(vacationMRepository.findByMemberM(any())).willReturn(Optional.ofNullable(vacationM));

        // when
        RuntimeException unexpectedUserException = assertThrows(RuntimeException.class, () -> {
            vacationService.requestVacation(daysVacationDto);
        });

        // then
        String message = unexpectedUserException.getMessage();
        assertEquals("휴가기간이 잘못 설정되었습니다.", message);
    }

    @Test
    public void 휴가신청_연도오류() {
        // given
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        VacationRequestDto daysVacationDto = createDaysVacationDto();
        daysVacationDto.setStartDate(LocalDate.parse("2021-12-31"));
        daysVacationDto.setEndDate(LocalDate.parse("2022-01-02"));

        MemberDto memberDto = createMemberDto();
        MemberM memberM = createMemberM(memberDto, passwordEncoder);
        MemberVacationM vacationM = createVacationM(memberM);

        utilities.when(SecurityUtil::getCurrentUsername).thenReturn(Optional.ofNullable("hojong"));
        given(historyRepository.existsHistoryByVacationRequestDto(any(), any())).willReturn(false);
        given(memberMRepository.findOneByName(any())).willReturn(Optional.ofNullable(memberM));
        given(vacationMRepository.findByMemberM(any())).willReturn(Optional.ofNullable(vacationM));

        // when
        RuntimeException unexpectedUserException = assertThrows(RuntimeException.class, () -> {
            vacationService.requestVacation(daysVacationDto);
        });

        // then
        String message = unexpectedUserException.getMessage();
        assertEquals("휴가 시작일자와 종료일자는 같은 연도여야 합니다.", message);
    }

    @Test
    public void 휴가신청_휴가일수초과() {
        // given
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        VacationRequestDto daysVacationDto = createDaysVacationDto();
        daysVacationDto.setStartDate(LocalDate.parse("2021-05-10"));
        daysVacationDto.setEndDate(LocalDate.parse("2021-05-30"));

        MemberDto memberDto = createMemberDto();
        MemberM memberM = createMemberM(memberDto, passwordEncoder);
        MemberVacationM vacationM = createVacationM(memberM);

        utilities.when(SecurityUtil::getCurrentUsername).thenReturn(Optional.ofNullable("hojong"));
        given(historyRepository.existsHistoryByVacationRequestDto(any(), any())).willReturn(false);
        given(memberMRepository.findOneByName(any())).willReturn(Optional.ofNullable(memberM));
        given(vacationMRepository.findByMemberM(any())).willReturn(Optional.ofNullable(vacationM));

        // when
        RuntimeException unexpectedUserException = assertThrows(RuntimeException.class, () -> {
            vacationService.requestVacation(daysVacationDto);
        });

        // then
        String message = unexpectedUserException.getMessage();
        assertEquals("휴가일수가 부족합니다.", message);
    }

    @Test
    public void 휴가취소_정상() {
        Long historyId = 1l;
        String name = "hojong";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        MemberDto memberDto = createMemberDto();
        MemberM memberM = createMemberM(memberDto, passwordEncoder);
        MemberVacationM vacationM = createVacationM(memberM);

        MemberVacationHistory history = createHistory(vacationM);

        utilities.when(SecurityUtil::getCurrentUsername).thenReturn(Optional.ofNullable(name));
        given(historyRepository
                .findByHistoryIdAndRequestStatus(historyId, VacationStatusCode.BEFORE.name(), name))
                .willReturn(Optional.ofNullable(history));

        // when
        BigDecimal remainCount = vacationService.cancelVacation(historyId);

        // then
        assertEquals(vacationM.getUseCount(), remainCount);
    }

    @Test
    public void 휴가취소_미회원() {
        Long historyId = 1l;

        utilities.when(SecurityUtil::getCurrentUsername).thenReturn(Optional.ofNullable(null));

        // when
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            vacationService.cancelVacation(historyId);
        });

        // then
        String message = runtimeException.getMessage();
        assertEquals("알 수 없는 사용자입니다.", message);
    }

    @Test
    public void 휴가취소_휴가없음() {
        Long historyId = 1l;
        String name = "hojong";
        MemberVacationHistory history = null;

        utilities.when(SecurityUtil::getCurrentUsername).thenReturn(Optional.ofNullable(name));
        given(historyRepository
                .findByHistoryIdAndRequestStatus(historyId, VacationStatusCode.BEFORE.name(), name))
                .willReturn(Optional.ofNullable(history));

        // when
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            vacationService.cancelVacation(historyId);
        });

        // then
        String message = runtimeException.getMessage();
        assertEquals("취소가능한 휴가가 없습니다.", message);
    }

    @Test
    public void 휴가조회_정상() {
        String name = "hojong";
        String vacationYear = "2021";
        VacationDto vacationDto = new VacationDto();
        List<VacationHistoryDto> histories = null;

        utilities.when(SecurityUtil::getCurrentUsername).thenReturn(Optional.ofNullable(name));
        given(vacationMRepository.findVacationByMemberNameAndVacationYear(name, vacationYear))
                .willReturn(vacationDto);
        given(historyRepository.findByNameAndYear(name, vacationYear))
                .willReturn(histories);

        // when
        vacationService.readVacations(vacationYear);
    }

    private MemberM createMemberM(MemberDto memberDto, BCryptPasswordEncoder passwordEncoder) {
        return MemberM.builder()
                .id(1l)
                .name(memberDto.getName())
                .password(passwordEncoder.encode(memberDto.getPassword()))
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
        vacationM.setTotalCount(BigDecimal.TEN);
        vacationM.setUseCount(BigDecimal.ZERO);
        vacationM.setMemberVacationId(1L);
        vacationM.setVacationYear(String.valueOf(LocalDate.now().getYear()));

        return vacationM;
    }

    private MemberVacationHistory createHistory(MemberVacationM vacationM) {
        MemberVacationHistory history = new MemberVacationHistory();
        history.setHistoryId(1l);
        history.setStartDate(LocalDate.parse("2021-05-10"));
        history.setEndDate(LocalDate.parse("2021-05-11"));
        history.setVacationDays(BigDecimal.valueOf(2l));
        history.setRequestStatus(VacationStatusCode.BEFORE.name());
        history.setMemo("memo");
        history.setMemberVacationM(vacationM);
        history.setRegDt(LocalDateTime.now());
        history.setModDt(LocalDateTime.now());

        return history;
    }

    private VacationRequestDto createDaysVacationDto() {
        return VacationRequestDto.builder().startDate(LocalDate.parse("2021-05-10"))
                .endDate(LocalDate.now().plusDays(7))
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
