package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VacationDto {
    private String name;
    private BigDecimal totalCount;
    private BigDecimal useCount;
    private String vacationYear;
}
