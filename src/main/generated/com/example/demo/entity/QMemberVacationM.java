package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberVacationM is a Querydsl query type for MemberVacationM
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMemberVacationM extends EntityPathBase<MemberVacationM> {

    private static final long serialVersionUID = 352404325L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberVacationM memberVacationM = new QMemberVacationM("memberVacationM");

    public final QMemberM memberM;

    public final NumberPath<Long> memberVacationId = createNumber("memberVacationId", Long.class);

    public final NumberPath<Double> totalCount = createNumber("totalCount", Double.class);

    public final NumberPath<Double> useCount = createNumber("useCount", Double.class);

    public final StringPath vacationYear = createString("vacationYear");

    public QMemberVacationM(String variable) {
        this(MemberVacationM.class, forVariable(variable), INITS);
    }

    public QMemberVacationM(Path<? extends MemberVacationM> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberVacationM(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberVacationM(PathMetadata metadata, PathInits inits) {
        this(MemberVacationM.class, metadata, inits);
    }

    public QMemberVacationM(Class<? extends MemberVacationM> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberM = inits.isInitialized("memberM") ? new QMemberM(forProperty("memberM")) : null;
    }

}

