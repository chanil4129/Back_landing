package osteam.backland.domain.person.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.repository.custom.PersonOnlyRepositoryCustom;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static osteam.backland.domain.person.entity.QPersonOnly.personOnly;

@RequiredArgsConstructor
public class PersonOnlyRepositoryImpl implements PersonOnlyRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Set<PersonOnly> searchByName(String name) {
        List<PersonOnly> result= jpaQueryFactory
                .selectFrom(personOnly)
                .where(personOnly.name.like("%" + name + "%"))
                .fetch();
        return new HashSet<>(result);
    }

    @Override
    public Set<PersonOnly> searchByPhone(String phone) {
        List<PersonOnly> result = jpaQueryFactory
                .selectFrom(personOnly)
                .where(personOnly.phone.like("%" + phone + "%"))
                .fetch();
        return new HashSet<>(result);
    }
}
