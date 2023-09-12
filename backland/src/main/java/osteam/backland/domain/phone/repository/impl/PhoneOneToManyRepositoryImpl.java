package osteam.backland.domain.phone.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.repository.custom.PhoneOneToManyRepositoryCustom;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static osteam.backland.domain.person.entity.QPersonOneToMany.personOneToMany;
import static osteam.backland.domain.phone.entity.QPhoneOneToMany.phoneOneToMany;

@RequiredArgsConstructor
public class PhoneOneToManyRepositoryImpl implements PhoneOneToManyRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Set<PhoneOneToMany> searchByNameContaining(String name) {
        List<PhoneOneToMany> results = jpaQueryFactory
                .selectFrom(phoneOneToMany)
                .join(phoneOneToMany.personOneToMany, personOneToMany)
                .where(personOneToMany.name.like("%" + name + "%"))
                .fetch();
        return new HashSet<>(results);
    }
}
