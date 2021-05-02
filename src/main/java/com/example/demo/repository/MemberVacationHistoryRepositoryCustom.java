package com.example.demo.repository;

import com.example.demo.common.code.VacationStatusCode;
import com.example.demo.dto.VacationRequestDto;
import com.example.demo.entity.MemberVacationHistory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberVacationHistoryRepositoryCustom {
    List<MemberVacationHistory> findHistoryListByStatusCode(VacationStatusCode statusCode, LocalDate localDate);

    boolean existsHistoryByVacationRequestDto(VacationRequestDto vacationRequestDto);
}
