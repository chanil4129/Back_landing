package osteam.backland.domain.phone.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import osteam.backland.domain.phone.entity.PhoneOneToOne;
import osteam.backland.domain.phone.repository.custom.PhoneOneToOneRepositoryCustom;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static osteam.backland.domain.person.entity.QPersonOneToOne.personOneToOne;
import static osteam.backland.domain.phone.entity.QPhoneOneToOne.phoneOneToOne;

@RequiredArgsConstructor
public class PhoneOneToOneRepositoryImpl implements PhoneOneToOneRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Set<PhoneOneToOne> searchByName(String name) {
        List<PhoneOneToOne> results = jpaQueryFactory
                .selectFrom(phoneOneToOne)
                .join(phoneOneToOne.personOneToOne, personOneToOne)
                .where(personOneToOne.name.eq(name))
                .fetch();
        return new HashSet<>(results);
    }
}
