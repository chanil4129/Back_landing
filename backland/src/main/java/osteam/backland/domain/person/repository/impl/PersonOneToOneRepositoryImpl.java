package osteam.backland.domain.person.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.repository.custom.PersonOneToOneRepositoryCustom;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static osteam.backland.domain.person.entity.QPersonOneToOne.personOneToOne;
import static osteam.backland.domain.phone.entity.QPhoneOneToOne.phoneOneToOne;

@RequiredArgsConstructor
public class PersonOneToOneRepositoryImpl implements PersonOneToOneRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Set<PersonOneToOne> searchByPhoneContaining(String phone) {
        List<PersonOneToOne> results = jpaQueryFactory
                .selectFrom(personOneToOne)
                .join(personOneToOne.phoneOneToOne, phoneOneToOne)
                .where(phoneOneToOne.phone.like("%" + phone + "%"))
                .fetch();
        return new HashSet<>(results);
    }

    @Override
    public PersonOneToOne searchByPhone(String phone) {
        return jpaQueryFactory
                .selectFrom(personOneToOne)
                .join(personOneToOne.personOneToOne,personOneToOne)
                .where(phoneOneToOne.phone.eq(phone))
                .fetchOne();
    }
}
