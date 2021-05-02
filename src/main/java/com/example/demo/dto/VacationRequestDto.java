package com.example.demo.dto;

import com.example.demo.common.code.VacationTypeCode;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Data
public class VacationRequestDto {
    @NotNull
    private String name;
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
                for(LocalDate checkDate = startDate; checkDate.isEqual(endDate); checkDate.plusDays(1)) {
                    if(!checkDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                            && !checkDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                        useDays++;
                    }
                }
                return BigDecimal.valueOf(useDays);
            case HALF:
                return BigDecimal.valueOf(0.5);
            case QUARTER:
                return BigDecimal.valueOf(0.25);
            default:
                throw new RuntimeException("알 수 없는 휴가코드");
        }
    }
}
