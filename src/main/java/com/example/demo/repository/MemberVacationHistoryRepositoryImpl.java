package com.example.demo.repository;

import com.example.demo.common.code.VacationStatusCode;
import com.example.demo.common.code.VacationTypeCode;
import com.example.demo.dto.VacationHistoryDto;
import com.example.demo.dto.VacationRequestDto;
import com.example.demo.entity.MemberVacationHistory;
import com.example.demo.entity.QMemberM;
import com.example.demo.entity.QMemberVacationHistory;
import com.example.demo.entity.QMemberVacationM;
import com.querydsl.core.types.Projections;
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

    @Override
    public boolean existsHistoryByVacationRequestDto(VacationRequestDto vacationRequestDto) {
        QMemberM qMemberM = QMemberM.memberM;
        QMemberVacationM qVacationM = QMemberVacationM.memberVacationM;
        QMemberVacationHistory qHistory = QMemberVacationHistory.memberVacationHistory;
        MemberVacationHistory history = null;

        if(VacationTypeCode.DAYS.equals(vacationRequestDto.getTypeCode())) {
            history = from(qHistory)
                    .innerJoin(qVacationM).on(qHistory.memberVacationM.eq(qVacationM))
                    .innerJoin(qMemberM).on(qVacationM.memberM.eq(qMemberM))
                    .where(qHistory.requestStatus.eq(VacationStatusCode.BEFORE.name())
                            .and(qMemberM.name.eq(vacationRequestDto.getName()))
                            .and(qHistory.startDate.goe(vacationRequestDto.getStartDate()))
                            .and(qHistory.endDate.loe(vacationRequestDto.getEndDate()))
                    ).fetchFirst();
        } else if(VacationTypeCode.HALF.equals(vacationRequestDto.getTypeCode())
                || VacationTypeCode.QUARTER.equals(vacationRequestDto.getTypeCode())) {
            history = from(qHistory)
                    .innerJoin(qVacationM).on(qHistory.memberVacationM.eq(qVacationM))
                    .innerJoin(qMemberM).on(qVacationM.memberM.eq(qMemberM))
                    .where(qHistory.requestStatus.eq(VacationStatusCode.BEFORE.name())
                            .and(qMemberM.name.eq(vacationRequestDto.getName()))
                            .and(qHistory.startDate.eq(vacationRequestDto.getStartDate()))
                    ).fetchFirst();
        }

        return !(history == null);
    }

    @Override
    public List<VacationHistoryDto> findByNameAndYear(String name, String year) {
        QMemberM qMemberM = QMemberM.memberM;
        QMemberVacationM qVacationM = QMemberVacationM.memberVacationM;
        QMemberVacationHistory qHistory = QMemberVacationHistory.memberVacationHistory;

        return from(qHistory).innerJoin(qVacationM).on(qHistory.memberVacationM.eq(qVacationM))
                .innerJoin(qMemberM).on(qVacationM.memberM.eq(qMemberM))
                .select(Projections.constructor(VacationHistoryDto.class,
                        qHistory.historyId, qHistory.requestStatus,
                        qHistory.startDate, qHistory.endDate,
                        qHistory.vacationDays, qHistory.memo,
                        qHistory.regDt, qHistory.modDt))
                .where(qMemberM.name.eq(name), qVacationM.vacationYear.eq(year)).fetch();
    }
}
