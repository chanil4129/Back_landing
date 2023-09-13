package osteam.backland.domain.person.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.entity.PhoneOneToOne;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonCreateService {
    private final PersonOnlyRepository personOnlyRepository;
    private final PersonOneToOneRepository personOneToOneRepository;
    private final PersonOneToManyRepository personOneToManyRepository;

    public PersonDTO createAll(PersonDTO personDTO) {
        one(personDTO);
        oneToOne(personDTO);
        oneToMany(personDTO);

        return personDTO;
    }

    /**
     * Phone과 OneToOne 관계인 person 생성
     */
    public PersonDTO oneToOne(PersonDTO personDTO) {
        log.debug("OneToOne 등록");
        PersonOneToOne personOneToOne = new PersonOneToOne();
        personOneToOne.setName(personDTO.getName());
        PhoneOneToOne phoneOneToOne = new PhoneOneToOne();
        phoneOneToOne.setPhone(personDTO.getPhone());
        personOneToOne.updatePhoneOneToOne(phoneOneToOne);
        personOneToOneRepository.save(personOneToOne);

        return new PersonDTO(personDTO.getName(),personDTO.getPhone());
    }

    /**
     * Phone과 OneToMany 관계인 person 생성
     */
    public PersonDTO oneToMany(PersonDTO personDTO) {
        log.debug("OneToMany 등록");
        PersonOneToMany personOneToMany = new PersonOneToMany();
        personOneToMany.setName(personDTO.getName());
        PhoneOneToMany phoneOneToMany = new PhoneOneToMany();
        phoneOneToMany.setPhone(personDTO.getPhone());
        personOneToMany.addPhoneOneToMany(phoneOneToMany);
        personOneToManyRepository.save(personOneToMany);

        return new PersonDTO(personDTO.getName(),personDTO.getPhone());
    }

    /**
     * person 하나로만 구성되어 있는 생성
     */
    public PersonDTO one(PersonDTO personDTO) {
        log.debug("One 등록");
        PersonOnly personOnly = new PersonOnly();
        personOnly.setName(personDTO.getName());
        personOnly.setPhone(personDTO.getPhone());
        personOnlyRepository.save(personOnly);

        return new PersonDTO(personDTO.getName(),personDTO.getPhone());
    }
}
