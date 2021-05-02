package com.example.demo.repository;


import com.example.demo.dto.SearchDTO;
import com.example.demo.entity.MemberVacationM;
import com.example.demo.entity.QMemberM;
import com.example.demo.entity.QMemberVacationM;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


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
    public List<MemberVacationM> findVacationByMemberId(SearchDTO searchDTO) {
        QMemberM qMemberM = QMemberM.memberM;
        QMemberVacationM qMemberVacationM =QMemberVacationM.memberVacationM;

        return from(qMemberVacationM).innerJoin(qMemberM).on(qMemberVacationM.memberM.eq(qMemberM))
                .where(qMemberVacationM.vacationYear.eq(searchDTO.getVacationYear()))
                .fetch();
    }
}
