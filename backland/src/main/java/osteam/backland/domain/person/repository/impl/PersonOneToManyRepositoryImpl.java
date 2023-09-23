package osteam.backland.domain.person.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.repository.custom.PersonOneToManyRepositoryCustom;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static osteam.backland.domain.person.entity.QPersonOneToMany.personOneToMany;
import static osteam.backland.domain.phone.entity.QPhoneOneToMany.phoneOneToMany;

@RequiredArgsConstructor
public class PersonOneToManyRepositoryImpl implements PersonOneToManyRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<PersonOneToMany> searchByPhoneContaining(String phone) {
        List<PersonOneToMany> results = jpaQueryFactory
                .selectFrom(personOneToMany)
                .join(personOneToMany.phoneOneToMany, phoneOneToMany)
                .where(phoneOneToMany.phone.contains(phone))
                .fetch();
        return new HashSet<>(results);
    }

    @Override
    public Optional<PersonOneToMany> searchByPhone(String phone) {
        PersonOneToMany result = jpaQueryFactory
                .selectFrom(personOneToMany)
                .join(personOneToMany.phoneOneToMany, phoneOneToMany)
                .where(phoneOneToMany.phone.eq(phone))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public void updateName(String phone, String newName) {
        jpaQueryFactory
                .update(personOneToMany)
                .set(personOneToMany.name, newName)
                .where(personOneToMany.phoneOneToMany.any().phone.eq(phone))
                .execute();

        entityManager.flush();
        entityManager.clear();
    }
}
