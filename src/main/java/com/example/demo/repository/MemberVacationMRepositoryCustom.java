package com.example.demo.repository;


import com.example.demo.entity.MemberVacationM;

import java.util.List;


public interface MemberVacationMRepositoryCustom {
    List<MemberVacationM> findVacationByMemberId(Long memberId);
}
