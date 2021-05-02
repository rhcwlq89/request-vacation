package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VacationDto {
    private Long memberId;
    private BigDecimal totalCount;
    private BigDecimal useCount;
    private String vacationYear;
    private List<HistoryDto> historyDtoList;

    public static class HistoryDto {
        private Long historyId;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal useDays;
    }
}
