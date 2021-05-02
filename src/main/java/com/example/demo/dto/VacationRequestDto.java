package com.example.demo.dto;

import com.example.demo.common.code.VacationTypeCode;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VacationRequestDto {
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
