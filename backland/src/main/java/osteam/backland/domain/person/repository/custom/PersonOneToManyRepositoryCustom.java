package osteam.backland.domain.person.repository.custom;

import osteam.backland.domain.person.entity.PersonOneToMany;

import java.util.Optional;
import java.util.Set;

public interface PersonOneToManyRepositoryCustom {
    Set<PersonOneToMany> searchByPhoneContaining(String phone);
    Optional<PersonOneToMany> searchByPhone(String phone);
    Long updateName(String phone, String newName);
}
