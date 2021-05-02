package com.example.demo.repository;

import com.example.demo.entity.MemberVacationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberVacationHistoryRepository extends JpaRepository<MemberVacationHistory, Long>,
        MemberVacationHistoryRepositoryCustom {
    MemberVacationHistory findByHistoryIdAndRequestStatus(Long historyId, String requestStatus);
}
