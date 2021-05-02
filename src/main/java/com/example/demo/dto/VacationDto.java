package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class VacationDto {
    private String name;
    private BigDecimal totalCount;
    private BigDecimal useCount;
    private String vacationYear;
    private List<VacationHistoryDto> histories;

    public VacationDto(String name, BigDecimal totalCount, BigDecimal useCount, String vacationYear) {
        this.name = name;
        this.totalCount = totalCount;
        this.useCount = useCount;
        this.vacationYear = vacationYear;
    }
}
