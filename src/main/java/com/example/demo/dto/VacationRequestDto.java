package com.example.demo.dto;

import com.example.demo.common.code.VacationTypeCode;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VacationRequestDto {
//    휴가 신청시 시작일, 종료일(반차/반반차의 경우는 필요없음), 사용 일수, 코멘트(선택 항목)를 입력합니다.
    private Long memberId;
    private LocalDate startDate;
    private LocalDate endDate;
    private VacationTypeCode typeCode;
    private Double useDays;
    private String memo;
}
