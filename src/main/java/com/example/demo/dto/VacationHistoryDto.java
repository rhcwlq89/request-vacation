package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VacationHistoryDto {
    private Long historyId;
    private String vacationStatusCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal vacationDays;
    private String memo;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
}
