package com.example.demo.repository;


import com.example.demo.entity.MemberVacationM;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public List<MemberVacationM> findVacationByMemberId(Long memberId) {
        return null;
    }
}
