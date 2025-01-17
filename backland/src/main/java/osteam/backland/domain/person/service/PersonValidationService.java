package osteam.backland.domain.person.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.entity.dto.PersonOneToManyDTO;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;
import osteam.backland.domain.phone.entity.PhoneOneToMany;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PersonValidationService {
    private final PersonOnlyRepository personOnlyRepository;
    private final PersonOneToOneRepository personOneToOneRepository;
    private final PersonOneToManyRepository personOneToManyRepository;

    public Optional<PersonDTO> duplicatePersonOnly(String phone) {
        Optional<PersonOnly> person = personOnlyRepository.searchByPhone(phone);
        return person.map(p -> {
            log.debug("DuplicatePersonOnly");
            return PersonDTO.builder()
                    .name(p.getName())
                    .phone(p.getPhone())
                    .build();
        });
    }

    public Optional<PersonDTO> duplicatePersonOneToOne(String phone) {
        Optional<PersonOneToOne> person = personOneToOneRepository.searchByPhone(phone);
        return person.map(p -> {
            log.debug("DuplicatePersonOneToOne");
            return PersonDTO.builder()
                    .name(p.getName())
                    .phone(p.getPhoneOneToOne().getPhone())
                    .build();
        });
    }

    public Optional<PersonOneToManyDTO> duplicatePersonOneToMany(String phone) {
        Optional<PersonOneToMany> person = personOneToManyRepository.searchByPhone(phone);
        if (person.isPresent()) {
            log.debug("DuplicatePersonOneToMany");
            PersonOneToManyDTO dto = new PersonOneToManyDTO();
            dto.setName(person.get().getName());
            dto.setPhone(person.get().getPhoneOneToMany()
                    .stream()
                    .map(PhoneOneToMany::getPhone)
                    .collect(Collectors.toList())
            );
            return Optional.of(dto);
        }
        return Optional.empty();
    }
}
