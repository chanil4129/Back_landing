package osteam.backland.domain.person.repository.custom;

import osteam.backland.domain.person.entity.PersonOnly;

import java.util.Set;

public interface PersonOnlyRepositoryCustom {
    Set<PersonOnly> searchByNameContaining(String name);
    Set<PersonOnly> searchByPhoneContaining(String phone);
    PersonOnly searchByPhone(String phone);
}
