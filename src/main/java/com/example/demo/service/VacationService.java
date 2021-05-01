package com.example.demo.service;

import com.example.demo.dto.VacationRequestDto;
import com.example.demo.repository.MemberVacationHistoryRepository;
import com.example.demo.repository.MemberVacationMRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class VacationService {
    private final MemberVacationMRepository vacationMRepository;
    private final MemberVacationHistoryRepository historyRepository;

//        사용자는 연차/반차(0.5일)/반반차(0.25일)에 해당하는 휴가를 신청할 수 있습니다.
//        휴가 신청시 남은 연차를 표시합니다.
//        연차를 모두 사용한 경우 휴가를 신청할 수 없습니다.
//        추가 기능: 사용 일수를 입력하는 대신 시작일, 종료일을 가지고 공휴일을 제외하고 계산해도 됩니다.
//        아직 시작하지 않은 휴가는 취소할 수 있습니다.
    public void requestVacation(VacationRequestDto vacationRequestDto) {

    }
}
