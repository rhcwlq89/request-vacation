package com.example.demo.repository;

import com.example.demo.common.code.VacationStatusCode;
import com.example.demo.dto.VacationHistoryDto;
import com.example.demo.dto.VacationRequestDto;
import com.example.demo.entity.MemberVacationHistory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberVacationHistoryRepositoryCustom {
    List<VacationHistoryDto> findByNameAndYear(String name, String year);

    List<MemberVacationHistory> findHistoryListByStatusCode(VacationStatusCode statusCode, LocalDate localDate);

    boolean existsHistoryByVacationRequestDto(VacationRequestDto vacationRequestDto, String name);

    Optional<MemberVacationHistory> findByHistoryIdAndRequestStatus(Long historyId, String requestStatus, String name);
}
