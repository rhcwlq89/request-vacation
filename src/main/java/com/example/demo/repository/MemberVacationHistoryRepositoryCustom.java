package com.example.demo.repository;

import com.example.demo.common.code.VacationStatusCode;
import com.example.demo.dto.VacationHistoryDto;
import com.example.demo.dto.VacationRequestDto;
import com.example.demo.entity.MemberVacationHistory;

import java.time.LocalDate;
import java.util.List;

public interface MemberVacationHistoryRepositoryCustom {
    List<VacationHistoryDto> findByNameAndYear(String name, String year);

    List<MemberVacationHistory> findHistoryListByStatusCode(VacationStatusCode statusCode, LocalDate localDate);

    boolean existsHistoryByVacationRequestDto(VacationRequestDto vacationRequestDto);
}
