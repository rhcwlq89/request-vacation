package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class VacationDto {
    private String name;
    private BigDecimal totalCount;
    private BigDecimal useCount;
    private String vacationYear;
}
