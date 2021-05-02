package com.example.demo.repository;

import com.example.demo.entity.MemberVacationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberVacationHistoryRepository extends JpaRepository<MemberVacationHistory, Long>,
        MemberVacationHistoryRepositoryCustom {
    Optional<MemberVacationHistory> findByHistoryIdAndRequestStatus(Long historyId, String requestStatus);
}
