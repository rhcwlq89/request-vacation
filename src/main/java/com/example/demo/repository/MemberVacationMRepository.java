package com.example.demo.repository;

import com.example.demo.entity.MemberM;
import com.example.demo.entity.MemberVacationM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberVacationMRepository extends JpaRepository<MemberVacationM, Long>, MemberVacationMRepositoryCustom {
    Optional<MemberVacationM> findByMemberM(MemberM member);
}
