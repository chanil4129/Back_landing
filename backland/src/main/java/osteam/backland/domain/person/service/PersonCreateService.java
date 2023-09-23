package osteam.backland.domain.person.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.entity.PhoneOneToOne;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PersonCreateService {
    private final PersonOnlyRepository personOnlyRepository;
    private final PersonOneToOneRepository personOneToOneRepository;
    private final PersonOneToManyRepository personOneToManyRepository;
    private final PersonUpdateService personUpdateService;
    private final PersonValidationService personValidationService;

    public PersonDTO createAll(PersonDTO personDTO) {
        oneCreate(personDTO);
        oneToOneCreate(personDTO);
        oneToManyCreate(personDTO);

        return personDTO;
    }

    /**
     * Phone과 OneToOne 관계인 person 생성
     */
    private PersonDTO oneToOne(PersonDTO personDTO) {
        log.debug("OneToOne 등록");
        PersonOneToOne personOneToOne = PersonOneToOne.builder()
                .name(personDTO.getName())
                .phoneOneToOne(PhoneOneToOne.builder()
                        .phone(personDTO.getPhone())
                        .build())
                .build();
        personOneToOneRepository.save(personOneToOne);
        return new PersonDTO(personDTO.getName(), personDTO.getPhone());
    }

    /**
     * Phone과 OneToMany 관계인 person 생성
     */
    private PersonDTO oneToMany(PersonDTO personDTO) {
        log.debug("OneToMany 등록");
        PhoneOneToMany phoneOneToMany = PhoneOneToMany.builder()
                .phone(personDTO.getPhone())
                .build();
        PersonOneToMany personOneToMany = PersonOneToMany.builder()
                .name(personDTO.getName())
                .phoneOneToMany(Collections.singleton(phoneOneToMany))
                .build();
        personOneToManyRepository.save(personOneToMany);
        return new PersonDTO(personDTO.getName(), personDTO.getPhone());
    }

    /**
     * person 하나로만 구성되어 있는 생성
     */
    private PersonDTO one(PersonDTO personDTO) {
        log.debug("One 등록");
        PersonOnly personOnly = PersonOnly.builder()
                .name(personDTO.getName())
                .phone(personDTO.getPhone())
                .build();
        personOnlyRepository.save(personOnly);
        return new PersonDTO(personDTO.getName(), personDTO.getPhone());
    }

    /**
     * phone 중복 검사 후, 존재하면 update, 아니면 create 하기
     * @param personDTO
     * @return
     */
    public PersonDTO oneToOneCreate(PersonDTO personDTO) {
        if (personValidationService.duplicatePersonOneToOne(personDTO.getPhone()).isPresent()) {
            log.debug("PersonOneToOne 중복 발생");
            personUpdateService.updateNamePersonOneToOne(personDTO);
            return personDTO;
        }
        return oneToOne(personDTO);
    }

    /**
     * phone 중복 검사 후, 존재하면 update, 아니면 create 하기
     * @param personDTO
     * @return
     */
    public PersonDTO oneToManyCreate(PersonDTO personDTO) {
        if (personValidationService.duplicatePersonOneToMany(personDTO.getPhone()).isPresent()) {
            log.debug("PersonOneToMany 중복 발생");
            personUpdateService.updateNamePersonOneToMany(personDTO);
            return personDTO;
        }
        return oneToMany(personDTO);
    }

    /**
     * phone 중복 검사 후, 존재하면 update, 아니면 create 하기
     * @param personDTO
     * @return
     */
    public PersonDTO oneCreate(PersonDTO personDTO){
        if (personValidationService.duplicatePersonOnly(personDTO.getPhone()).isPresent()) {
            log.debug("PersonOnly 중복 발생");
            personUpdateService.updateNamePersonOnly(personDTO);
            return personDTO;
        }
        return one(personDTO);
    }
}
