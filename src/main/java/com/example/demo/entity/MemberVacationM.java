package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_vacation_m")
public class MemberVacationM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberVacationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private MemberM memberM;

    @Column
    private String vacationYear;

    @Column
    private BigDecimal totalCount;

    @Column
    private BigDecimal useCount;
}
