package com.example.demo.repository;

import com.example.demo.common.code.VacationStatusCode;
import com.example.demo.entity.MemberVacationHistory;
import com.example.demo.entity.QMemberVacationHistory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

public class MemberVacationHistoryRepositoryImpl extends QuerydslRepositorySupport implements MemberVacationHistoryRepositoryCustom {
    public MemberVacationHistoryRepositoryImpl() {
        super(MemberVacationHistory.class);
    }

    @PersistenceContext
    @Override
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

    @Override
    public List<MemberVacationHistory> findHistoryListByStatusCode(VacationStatusCode statusCode, LocalDate localDate) {
        QMemberVacationHistory qHistory = QMemberVacationHistory.memberVacationHistory;

        return from(qHistory)
                .where(qHistory.requestStatus.eq(statusCode.name()).and(qHistory.startDate.eq(localDate)))
                .fetch();
    }
}
