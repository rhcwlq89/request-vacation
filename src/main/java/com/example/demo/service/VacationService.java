package com.example.demo.service;

import com.example.demo.common.code.VacationStatusCode;
import com.example.demo.common.code.VacationTypeCode;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.VacationDto;
import com.example.demo.dto.VacationRequestDto;
import com.example.demo.entity.MemberM;
import com.example.demo.entity.MemberVacationHistory;
import com.example.demo.entity.MemberVacationM;
import com.example.demo.repository.MemberMRepository;
import com.example.demo.repository.MemberVacationHistoryRepository;
import com.example.demo.repository.MemberVacationMRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class VacationService {
    private final MemberMRepository memberMRepository;
    private final MemberVacationMRepository vacationMRepository;
    private final MemberVacationHistoryRepository historyRepository;





    @Transactional
    public void requestVacation(VacationRequestDto vacationRequestDto) {
        //        연차를 모두 사용한 경우 휴가를 신청할 수 없습니다.
        //        추가 기능: 사용 일수를 입력하는 대신 시작일, 종료일을 가지고 공휴일을 제외하고 계산해도 됩니다.
        // 중복날짜 계산
        Optional<MemberM> memberM = memberMRepository.findById(vacationRequestDto.getMemberId());
        memberM.ifPresent(member -> {
            log.info(member.getName());
            vacationMRepository.findByMemberM(member).ifPresent(vacationM -> {
                log.info(vacationM.getTotalCount().toString());
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
                    throw new RuntimeException("휴가일수 부족");
                }
            });
        });
    }

    @Transactional
    public void cancelVacation(Long historyId) {
        //        아직 시작하지 않은 휴가는 취소할 수 있습니다.
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
        //        휴가 신청시 남은 연차를 표시합니다.
        return vacationMRepository.findVacationByMemberId(searchDTO);
    }
}
