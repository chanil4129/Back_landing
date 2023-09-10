package osteam.backland.domain.person.repository.custom;

import osteam.backland.domain.person.entity.PersonOneToOne;

import java.util.Set;

public interface PersonOneToOneRepositoryCustom {
    Set<PersonOneToOne> searchByPhone(String phone);
}
