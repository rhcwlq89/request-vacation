package com.example.demo.repository;


import com.example.demo.dto.VacationDto;
import com.example.demo.entity.MemberVacationM;
import com.example.demo.entity.QMemberM;
import com.example.demo.entity.QMemberVacationM;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class MemberVacationMRepositoryImpl extends QuerydslRepositorySupport implements MemberVacationMRepositoryCustom{

    public MemberVacationMRepositoryImpl() {
        super(MemberVacationM.class);
    }

    @PersistenceContext
    @Override
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

    @Override
    public VacationDto findVacationByMemberNameAndVacationYear(String name, String year) {
        QMemberM qMemberM = QMemberM.memberM;
        QMemberVacationM qVacationM = QMemberVacationM.memberVacationM;

        return from(qVacationM).innerJoin(qMemberM).on(qVacationM.memberM.eq(qMemberM))
                .select(Projections.constructor(VacationDto.class, qMemberM.name, qVacationM.totalCount,
                        qVacationM.useCount, qVacationM.vacationYear))
                .where(qMemberM.name.eq(name), qVacationM.vacationYear.eq(year)).fetchFirst();
    }
}
