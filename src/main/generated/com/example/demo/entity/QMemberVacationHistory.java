package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberVacationHistory is a Querydsl query type for MemberVacationHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMemberVacationHistory extends EntityPathBase<MemberVacationHistory> {

    private static final long serialVersionUID = 1641090444L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberVacationHistory memberVacationHistory = new QMemberVacationHistory("memberVacationHistory");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> historyId = createNumber("historyId", Long.class);

    public final QMemberVacationM memberVacationM;

    public final DateTimePath<java.time.LocalDateTime> modDt = createDateTime("modDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> modId = createNumber("modId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> regId = createNumber("regId", Long.class);

    public final StringPath requestStatus = createString("requestStatus");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final NumberPath<Double> vacationDays = createNumber("vacationDays", Double.class);

    public QMemberVacationHistory(String variable) {
        this(MemberVacationHistory.class, forVariable(variable), INITS);
    }

    public QMemberVacationHistory(Path<? extends MemberVacationHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberVacationHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberVacationHistory(PathMetadata metadata, PathInits inits) {
        this(MemberVacationHistory.class, metadata, inits);
    }

    public QMemberVacationHistory(Class<? extends MemberVacationHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberVacationM = inits.isInitialized("memberVacationM") ? new QMemberVacationM(forProperty("memberVacationM"), inits.get("memberVacationM")) : null;
    }

}

