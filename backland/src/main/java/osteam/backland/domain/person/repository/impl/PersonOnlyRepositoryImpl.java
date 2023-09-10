package osteam.backland.domain.person.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.repository.custom.PersonOnlyRepositoryCustom;

import java.util.Set;

@RequiredArgsConstructor
public class PersonOnlyRepositoryImpl implements PersonOnlyRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Set<PersonOnly> searchByPhone(String phone) {
        return null;
    }
}
