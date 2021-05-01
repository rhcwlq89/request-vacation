package com.example.demo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberM is a Querydsl query type for MemberM
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMemberM extends EntityPathBase<MemberM> {

    private static final long serialVersionUID = 1273442242L;

    public static final QMemberM memberM = new QMemberM("memberM");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public QMemberM(String variable) {
        super(MemberM.class, forVariable(variable));
    }

    public QMemberM(Path<? extends MemberM> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberM(PathMetadata metadata) {
        super(MemberM.class, metadata);
    }

}

