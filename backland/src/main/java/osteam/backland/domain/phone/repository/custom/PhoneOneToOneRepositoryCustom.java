package osteam.backland.domain.phone.repository.custom;

import osteam.backland.domain.phone.entity.PhoneOneToOne;

import java.util.Set;

public interface PhoneOneToOneRepositoryCustom {
    Set<PhoneOneToOne> searchByName(String name);
}
