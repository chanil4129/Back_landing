package osteam.backland.domain.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.repository.custom.PersonOnlyRepositoryCustom;

import java.util.UUID;

@Repository
public interface PersonOnlyRepository extends JpaRepository<PersonOnly, UUID>, PersonOnlyRepositoryCustom {
}
