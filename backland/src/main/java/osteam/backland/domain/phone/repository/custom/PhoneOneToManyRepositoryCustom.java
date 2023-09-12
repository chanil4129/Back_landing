package osteam.backland.domain.phone.repository.custom;

import osteam.backland.domain.phone.entity.PhoneOneToMany;

import java.util.Set;

public interface PhoneOneToManyRepositoryCustom {
    Set<PhoneOneToMany> searchByNameContaining(String name);
}
