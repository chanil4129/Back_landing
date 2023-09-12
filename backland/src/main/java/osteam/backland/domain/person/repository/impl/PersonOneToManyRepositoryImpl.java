package osteam.backland.domain.person.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.repository.custom.PersonOneToManyRepositoryCustom;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static osteam.backland.domain.person.entity.QPersonOneToMany.personOneToMany;
import static osteam.backland.domain.phone.entity.QPhoneOneToMany.phoneOneToMany;

@RequiredArgsConstructor
public class PersonOneToManyRepositoryImpl implements PersonOneToManyRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Set<PersonOneToMany> searchByPhoneContaining(String phone) {
        List<PersonOneToMany> results = jpaQueryFactory
                .selectFrom(personOneToMany)
                .join(personOneToMany.phoneOneToMany, phoneOneToMany)
                .where(phoneOneToMany.phone.like("%" + phone + "%"))
                .fetch();
        return new HashSet<>(results);
    }

    @Override
    public PersonOneToMany searchByPhone(String phone) {
        return jpaQueryFactory
                .selectFrom(personOneToMany)
                .join(personOneToMany.phoneOneToMany,phoneOneToMany)
                .where(phoneOneToMany.phone.eq(phone))
                .fetchOne();
    }
}
