package com.example.demo.repository;


import com.example.demo.dto.VacationDto;


public interface MemberVacationMRepositoryCustom {
    VacationDto findVacationByMemberNameAndVacationYear(String name, String year);
}
