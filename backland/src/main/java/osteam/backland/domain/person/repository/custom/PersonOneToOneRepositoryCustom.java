package osteam.backland.domain.person.repository.custom;

import osteam.backland.domain.person.entity.PersonOneToOne;

import java.util.Set;

public interface PersonOneToOneRepositoryCustom {
    Set<PersonOneToOne> searchByPhoneContaining(String phone);
    PersonOneToOne searchByPhone(String phone);
}
