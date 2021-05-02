package com.example.demo.dto;

import com.example.demo.common.code.VacationStatusCode;
import com.example.demo.common.code.VacationTypeCode;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VacationRequestDto {
//    휴가 신청시 시작일, 종료일(반차/반반차의 경우는 필요없음), 사용 일수, 코멘트(선택 항목)를 입력합니다.
    private Long memberId;
    private LocalDate startDate;
    private LocalDate endDate;
    private VacationTypeCode typeCode;
    private BigDecimal useDays;
    private String memo;

    public BigDecimal getUseDays() {
        switch (typeCode) {
            case DAYS:
                return this.useDays;
            case HALF:
                return BigDecimal.valueOf(0.5);
            case QUARTER:
                return BigDecimal.valueOf(0.25);
            default:
                throw new RuntimeException("알 수 없는 휴가코드");
        }
    }
}
