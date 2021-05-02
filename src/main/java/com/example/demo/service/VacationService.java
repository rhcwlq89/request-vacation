package com.example.demo.service;

import com.example.demo.common.code.VacationStatusCode;
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

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class VacationService {
    private final MemberMRepository memberMRepository;
    private final MemberVacationMRepository vacationMRepository;
    private final MemberVacationHistoryRepository historyRepository;

//        사용자는 연차/반차(0.5일)/반반차(0.25일)에 해당하는 휴가를 신청할 수 있습니다.
//        휴가 신청시 남은 연차를 표시합니다.
//        연차를 모두 사용한 경우 휴가를 신청할 수 없습니다.
//        추가 기능: 사용 일수를 입력하는 대신 시작일, 종료일을 가지고 공휴일을 제외하고 계산해도 됩니다.
//        아직 시작하지 않은 휴가는 취소할 수 있습니다.
    public void requestVacation(VacationRequestDto vacationRequestDto) {
        Optional<MemberM> memberM = memberMRepository.findById(vacationRequestDto.getMemberId());
        memberM.ifPresent(member -> {
            vacationMRepository.findByMemberM(member).ifPresent(vacationM -> {
                if(vacationM.getTotalCount() >= vacationM.getUseCount() + vacationRequestDto.getUseDays()) {

                } else {
                    throw new RuntimeException("휴가일수 부족");
                }
            });
        });
    }

    public void cancelVacation(Long historyId) {
        MemberVacationHistory history = historyRepository.findById(historyId).orElseThrow(RuntimeException::new);
        history.setRequestStatus(VacationStatusCode.CANCEL);

        MemberVacationM memberVacationM = history.getMemberVacationM();
        double v = memberVacationM.getUseCount() - history.getVacationDays();
        memberVacationM.setUseCount(v);
    }

    public Page<VacationDto> readVacations(SearchDTO searchDTO) {
        List<MemberVacationM> vacationByMemberId = vacationMRepository.findVacationByMemberId(searchDTO.getMemberId());
        return null;
    }

    public VacationDto readVacation(Long historyId) {
        return null;
    }
}
