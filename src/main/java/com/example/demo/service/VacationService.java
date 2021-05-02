package com.example.demo.service;

import com.example.demo.common.code.VacationStatusCode;
import com.example.demo.common.code.VacationTypeCode;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.VacationRequestDto;
import com.example.demo.entity.MemberM;
import com.example.demo.entity.MemberVacationHistory;
import com.example.demo.entity.MemberVacationM;
import com.example.demo.repository.MemberMRepository;
import com.example.demo.repository.MemberVacationHistoryRepository;
import com.example.demo.repository.MemberVacationMRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void requestVacation(VacationRequestDto vacationRequestDto) {
        if(historyRepository.existsHistoryByVacationRequestDto(vacationRequestDto)) {
            throw new RuntimeException("중복된 휴가가 있습니다.");
        }

        if(vacationRequestDto.getUseDays().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("휴가기간이 잘못 설정되었습니다.");
        }

        memberMRepository.findOneByName(vacationRequestDto.getName()).ifPresent(member -> {
            vacationMRepository.findByMemberM(member).ifPresent(vacationM -> {
                if(vacationM.getTotalCount().compareTo(vacationM.getUseCount().add(vacationRequestDto.getUseDays())) >= 0) {
                    MemberVacationHistory history = new MemberVacationHistory();
                    history.setMemberVacationM(vacationM);
                    history.setRequestStatus(VacationStatusCode.BEFORE.name());
                    history.setStartDate(vacationRequestDto.getStartDate());
                    history.setEndDate(vacationRequestDto.getEndDate());
                    history.setVacationDays(vacationRequestDto.getUseDays());
                    historyRepository.save(history);

                    vacationM.setUseCount(vacationM.getUseCount().add(vacationRequestDto.getUseDays()));
                } else {
                    throw new RuntimeException("휴가일수가 부족합니다.");
                }
            });
        });
    }

    @Transactional
    public void cancelVacation(Long historyId) {
        MemberVacationHistory history = historyRepository
                .findByHistoryIdAndRequestStatus(historyId, VacationStatusCode.BEFORE.name());

        if(history == null) {
            throw new RuntimeException("취소가능한 휴가가 없습니다.");
        }

        if(!history.getStartDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("휴가가 시작되어 취소할 수 없습니다.");
        }

        history.setRequestStatus(VacationStatusCode.CANCEL.name());

        MemberVacationM memberVacationM = history.getMemberVacationM();
        BigDecimal subtract = memberVacationM.getUseCount().subtract(history.getVacationDays());
        memberVacationM.setUseCount(subtract) ;
    }

    @Transactional
    public List<MemberVacationM> readVacations(SearchDTO searchDTO) {
        return vacationMRepository.findVacationByMemberId(searchDTO);
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
