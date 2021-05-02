package com.example.demo.repository;


import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.VacationDto;
import com.example.demo.entity.MemberVacationM;

import java.util.List;


public interface MemberVacationMRepositoryCustom {
    List<MemberVacationM> findVacationByMemberId(SearchDTO searchDTO);

    VacationDto findVacationByMemberNameAndVacationYear(String name, String year);
}
