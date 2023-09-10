package osteam.backland.domain.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osteam.backland.domain.phone.entity.PhoneOneToOne;
import osteam.backland.domain.phone.repository.custom.PhoneOneToOneRepositoryCustom;

import java.util.UUID;

public interface PhoneOneToOneRepository extends JpaRepository<PhoneOneToOne, UUID>, PhoneOneToOneRepositoryCustom {
}
