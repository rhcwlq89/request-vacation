package com.example.demo.dto;

import com.example.demo.common.code.VacationTypeCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacationRequestDto {
    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull
    private VacationTypeCode typeCode;
    private String memo;

    public BigDecimal getUseDays() {
        switch (typeCode) {
            case DAYS:
                Long useDays = 0l;
                for(LocalDate checkDate = startDate; !checkDate.isAfter(endDate); checkDate = checkDate.plusDays(1)) {
                    if(!isWeekend(checkDate)) {
                        useDays++;
                    }
                }
                return BigDecimal.valueOf(useDays);
            case HALF:
                if(isWeekend(startDate)) {
                    return BigDecimal.ZERO;
                }
                return BigDecimal.valueOf(0.5);
            case QUARTER:
                if(isWeekend(startDate)) {
                    return BigDecimal.ZERO;
                }
                return BigDecimal.valueOf(0.25);
            default:
                throw new RuntimeException("알 수 없는 휴가코드입니다.");
        }
    }

    private boolean isWeekend(LocalDate date) {
        return startDate.getDayOfWeek().equals(DayOfWeek.SUNDAY) ||
                startDate.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }
}
