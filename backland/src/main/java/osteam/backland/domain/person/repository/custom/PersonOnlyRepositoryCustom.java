package osteam.backland.domain.person.repository.custom;

import osteam.backland.domain.person.entity.PersonOnly;

import java.util.Set;

public interface PersonOnlyRepositoryCustom {
    Set<PersonOnly> searchByPhone(String phone);
}
