package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(schema = "vacation", name = "member_vacation_history")
public class MemberVacationHistory {
    @Id
    @GeneratedValue
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberVacationId")
    @JsonBackReference
    private MemberVacationM memberVacationM;

    @Column
    private String requestStatus;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private Double vacationDays;

    @Column
    private Long regId;

    @Column
    private LocalDateTime regDt;

    @Column
    private Long modId;

    @Column
    private LocalDateTime modDt;
}
