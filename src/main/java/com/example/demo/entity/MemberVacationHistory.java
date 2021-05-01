package com.example.demo.entity;

import com.example.demo.common.code.VacationStatusCode;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(schema = "vacation", name = "member_vacation_history")
public class MemberVacationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberVacationId")
    @JsonBackReference
    private MemberVacationM memberVacationM;

    @Column
    private VacationStatusCode requestStatus;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private Double vacationDays;

    @CreatedDate
    @Column
    private LocalDateTime regDt;

    @LastModifiedDate
    @Column
    private LocalDateTime modDt;
}
