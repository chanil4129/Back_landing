package osteam.backland.domain.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.repository.custom.PhoneOneToManyRepositoryCustom;

import java.util.UUID;

public interface PhoneOneToManyRepository extends JpaRepository<PhoneOneToMany, UUID>, PhoneOneToManyRepositoryCustom{
}
