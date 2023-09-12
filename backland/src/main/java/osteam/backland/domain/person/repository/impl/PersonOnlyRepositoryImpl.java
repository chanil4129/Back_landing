package osteam.backland.domain.person.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.repository.custom.PersonOnlyRepositoryCustom;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static osteam.backland.domain.person.entity.QPersonOnly.personOnly;

@RequiredArgsConstructor
public class PersonOnlyRepositoryImpl implements PersonOnlyRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Set<PersonOnly> searchByNameContaining(String name) {
        List<PersonOnly> result= jpaQueryFactory
                .selectFrom(personOnly)
                .where(personOnly.name.like("%" + name + "%"))
                .fetch();
        return new HashSet<>(result);
    }

    @Override
    public Set<PersonOnly> searchByPhoneContaining(String phone) {
        List<PersonOnly> result = jpaQueryFactory
                .selectFrom(personOnly)
                .where(personOnly.phone.like("%" + phone + "%"))
                .fetch();
        return new HashSet<>(result);
    }

    @Override
    public Optional<PersonOnly> searchByPhone(String phone) {
        PersonOnly result = jpaQueryFactory
                .selectFrom(personOnly)
                .where(personOnly.phone.eq(phone))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Long updateName(String phone, String newName) {
        return jpaQueryFactory
                .update(personOnly)
                .set(personOnly.name, newName)
                .where(personOnly.phone.eq(phone))
                .execute();
    }
}
