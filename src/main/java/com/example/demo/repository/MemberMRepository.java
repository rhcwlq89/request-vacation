package com.example.demo.repository;

import com.example.demo.entity.MemberM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMRepository extends JpaRepository<MemberM, Long> {
}
