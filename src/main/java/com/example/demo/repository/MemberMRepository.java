package com.example.demo.repository;

import com.example.demo.entity.MemberM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberMRepository extends JpaRepository<MemberM, Long> {
    Optional<MemberM> findOneByName(String name);
}
