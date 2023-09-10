package osteam.backland.domain.person.repository.custom;

import osteam.backland.domain.person.entity.PersonOneToMany;

import java.util.Set;

public interface PersonOneToManyRepositoryCustom {
    Set<PersonOneToMany> searchByPhone(String phone);
}
