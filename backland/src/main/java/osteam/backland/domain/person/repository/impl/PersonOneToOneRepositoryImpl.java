package osteam.backland.domain.person.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.repository.custom.PersonOneToOneRepositoryCustom;

import java.util.*;

import static osteam.backland.domain.person.entity.QPersonOneToOne.personOneToOne;
import static osteam.backland.domain.phone.entity.QPhoneOneToOne.phoneOneToOne;

@RequiredArgsConstructor
public class PersonOneToOneRepositoryImpl implements PersonOneToOneRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<PersonOneToOne> searchByPhoneContaining(String phone) {
        List<PersonOneToOne> results = jpaQueryFactory
                .selectFrom(personOneToOne)
                .join(personOneToOne.phoneOneToOne, phoneOneToOne)
                .where(phoneOneToOne.phone.contains(phone))
                .fetch();
        return new HashSet<>(results);
    }

    @Override
    public Optional<PersonOneToOne> searchByPhone(String phone) {
        PersonOneToOne result = jpaQueryFactory
                .selectFrom(personOneToOne)
                .join(personOneToOne.phoneOneToOne, phoneOneToOne)
                .where(phoneOneToOne.phone.eq(phone))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public void updateName(String phone, String newName) {
        UUID personId = jpaQueryFactory
                .select(phoneOneToOne.personOneToOne.id)
                .from(phoneOneToOne)
                .where(phoneOneToOne.phone.eq(phone))
                .fetchOne();

        if (personId != null) {
            jpaQueryFactory
                    .update(personOneToOne)
                    .set(personOneToOne.name, newName)
                    .where(personOneToOne.id.eq(personId))
                    .execute();
        }
        entityManager.flush();
        entityManager.clear();
//        jpaQueryFactory
//                .update(personOneToOne)
//                .set(personOneToOne.name, newName)
//                .where(personOneToOne.phoneOneToOne.phone.eq(phone))
//                .execute();
    }
}
