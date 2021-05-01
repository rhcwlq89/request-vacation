package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(schema = "vacation", name = "member_vacation_m")
public class MemberVacationM {
    @Id @GeneratedValue
    private Long memberVacationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonBackReference
    private MemberM memberM;

    @Column
    private String vacationYear;

    @Column
    private Double totalCount;

    @Column
    private Double useCount;
}
