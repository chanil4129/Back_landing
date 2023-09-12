package osteam.backland.domain.person.repository.custom;

import osteam.backland.domain.person.entity.PersonOneToOne;

import java.util.Optional;
import java.util.Set;

public interface PersonOneToOneRepositoryCustom {
    Set<PersonOneToOne> searchByPhoneContaining(String phone);
    Optional<PersonOneToOne> searchByPhone(String phone);
    Long updateName(String phone, String newName);
}
