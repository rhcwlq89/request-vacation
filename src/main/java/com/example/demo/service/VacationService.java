package com.example.demo.service;

import com.example.demo.common.code.VacationStatusCode;
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class VacationService {
    private final MemberMRepository memberMRepository;
    private final MemberVacationMRepository vacationMRepository;
    private final MemberVacationHistoryRepository historyRepository;

    private final JdbcTemplate jdbcTemplate;

    private static String BULK_INSERT_VACATION = "INSERT INTO vacation.member_vacation_m " +
            "(member_id,vacation_year,total_count,use_count) VALUES " +
            "(?,?,?,?) ";

    @Transactional
    public VacationDto requestVacation(VacationRequestDto vacationRequestDto) {
        String name = SecurityUtil.getCurrentUsername().orElseThrow(() -> new RuntimeException("알 수 없는 사용자입니다."));
        LocalDate startDate = vacationRequestDto.getStartDate();
        LocalDate endDate = vacationRequestDto.getEndDate();
        BigDecimal useDays = vacationRequestDto.getUseDays();
        VacationDto result = new VacationDto();

        if(historyRepository.existsHistoryByVacationRequestDto(vacationRequestDto, name)) {
            throw new RuntimeException("중복된 휴가가 있습니다.");
        }

        if(useDays.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("휴가기간이 잘못 설정되었습니다.");
        }

        if(endDate != null && startDate.getYear() != endDate.getYear()) {
            throw new RuntimeException("휴가 시작일자와 종료일자는 같은 연도여야 합니다.");
        }

        memberMRepository.findOneByName(name).ifPresent(member -> {
            vacationMRepository.findByMemberM(member).ifPresent(vacationM -> {
                if(vacationM.getTotalCount().compareTo(vacationM.getUseCount().add(useDays)) >= 0) {
                    MemberVacationHistory history = new MemberVacationHistory();
                    history.setMemberVacationM(vacationM);
                    VacationStatusCode status = startDate.isAfter(LocalDate.now())
                            ? VacationStatusCode.BEFORE : VacationStatusCode.USED;
                    history.setRequestStatus(status.name());
                    history.setStartDate(startDate);
                    history.setEndDate(endDate);
                    history.setVacationDays(useDays);
                    history.setMemo(vacationRequestDto.getMemo());
                    historyRepository.save(history);

                    vacationM.setUseCount(vacationM.getUseCount().add(useDays));
                } else {
                    throw new RuntimeException("휴가일수가 부족합니다.");
                }
                result.setVacationYear(vacationM.getVacationYear());
                result.setTotalCount(vacationM.getTotalCount());
                result.setName(member.getName());
                result.setUseCount(vacationM.getUseCount());
            });
        });

        return result;
    }

    @Transactional
    public BigDecimal cancelVacation(Long historyId) {
        String name = SecurityUtil.getCurrentUsername().orElseThrow(() -> new RuntimeException("알 수 없는 사용자입니다."));
        MemberVacationHistory history = historyRepository
                .findByHistoryIdAndRequestStatus(historyId, VacationStatusCode.BEFORE.name(), name)
                .orElseThrow(() -> new RuntimeException("취소가능한 휴가가 없습니다."));
        history.setRequestStatus(VacationStatusCode.CANCEL.name());

        MemberVacationM memberVacationM = history.getMemberVacationM();
        BigDecimal remainCount = memberVacationM.getUseCount().subtract(history.getVacationDays());
        memberVacationM.setUseCount(remainCount) ;
        return remainCount;
    }

    @Transactional
    public VacationDto readVacations(String vacationYear) {
        String name = SecurityUtil.getCurrentUsername().orElseThrow(() -> new RuntimeException("알 수 없는 사용자입니다."));
        VacationDto vacationDto = vacationMRepository.findVacationByMemberNameAndVacationYear(name, vacationYear);
        List<VacationHistoryDto> histories = historyRepository.findByNameAndYear(name, vacationYear);
        vacationDto.setHistories(histories);
        return vacationDto;
    }

    @Transactional
    public void startVacation() {
        List<MemberVacationHistory> historyList =
                historyRepository.findHistoryListByStatusCode(VacationStatusCode.BEFORE, LocalDate.now());
        historyList.forEach(history -> {
            history.setRequestStatus(VacationStatusCode.USED.name());
        });
    }

    @Transactional
    public void offerVacation() {
        String year = String.valueOf(LocalDate.now().getYear() + 1);
        List<MemberM> all = memberMRepository.findAll();
        List<MemberVacationM> vacationMList = new ArrayList<>();

        all.forEach(memberM -> {
            MemberVacationM vacationM = new MemberVacationM();
            vacationM.setMemberM(memberM);
            vacationM.setVacationYear(year);
            vacationM.setTotalCount(BigDecimal.valueOf(15d));
            vacationM.setUseCount(BigDecimal.valueOf(0d));
            vacationMList.add(vacationM);
        });

        jdbcTemplate.batchUpdate(BULK_INSERT_VACATION, vacationMList, 500, (ps, vacationM) -> {
            ps.setLong(1, vacationM.getMemberM().getId());
            ps.setString(2, vacationM.getVacationYear());
            ps.setBigDecimal(3, vacationM.getTotalCount());
            ps.setBigDecimal(4, vacationM.getUseCount());
        });
    }
}
