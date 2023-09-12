package osteam.backland.domain.person.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.entity.dto.PersonOneToManyDTO;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.entity.PhoneOneToOne;
import osteam.backland.domain.phone.repository.PhoneOneToManyRepository;
import osteam.backland.domain.phone.repository.PhoneOneToOneRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonSearchService {
    private final PersonOnlyRepository personOnlyRepository;
    private final PersonOneToOneRepository personOneToOneRepository;
    private final PersonOneToManyRepository personOneToManyRepository;
    private final PhoneOneToManyRepository phoneOneToManyRepository;
    private final PhoneOneToOneRepository phoneOneToOneRepository;

    //**전체 출력**//
    public Set<PersonDTO> searchAllPersonOnly(){
        List<PersonOnly> personOnlies = personOnlyRepository.findAll();

        return personOnlies.stream()
                .map(person -> {
                    PersonDTO dto = new PersonDTO();
                    dto.setName(person.getName());
                     dto.setPhone(person.getPhone());
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    public Set<PersonDTO> searchAllPersonOneToOne(){
        List<PersonOneToOne> personOneToOnes = personOneToOneRepository.findAll();

        return personOneToOnes.stream()
                .map(person -> {
                    PersonDTO dto = new PersonDTO();
                    dto.setName(person.getName());
                    dto.setPhone(person.getPhoneOneToOne().getPhone());
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    public Set<PersonOneToManyDTO> searchAllPersonOneToMany(){
        List<PersonOneToMany> personOneToManies = personOneToManyRepository.findAll();

        return getPersonOneToManyDTOS(personOneToManies.stream(), new HashSet<>(personOneToManies));
    }

    //**이름 찾기**//
    public Set<PersonDTO> searchPersonOnlyByName(String name) {
        Set<PersonOnly> personOnlies = personOnlyRepository.searhByName(name);

        return personOnlies.stream()
                .map(person -> {
                    PersonDTO dto = new PersonDTO();
                    dto.setName(person.getName());
                    dto.setPhone(person.getPhone());
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    public Set<PersonDTO> searchPersonOneToOneByName(String name) {
        Set<PhoneOneToOne> phoneOneToOnes = phoneOneToOneRepository.searchByName(name);

        return phoneOneToOnes.stream()
                .map(phone -> {
                    PersonDTO dto = new PersonDTO();
                    dto.setName(phone.getPersonOneToOne().getName());
                    dto.setPhone(phone.getPhone());
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    public Set<PersonOneToManyDTO> searchPersonOneToManyByName(String name){
        Set<PersonOneToMany> persons = phoneOneToManyRepository.searchByName(name)
                .stream()
                .map(PhoneOneToMany::getPersonOneToMany)
                .collect(Collectors.toSet());

        return persons.stream()
                .map(person -> {
                    PersonOneToManyDTO dto = new PersonOneToManyDTO();
                    dto.setName(person.getName());

                    List<String> phoneNumbers = person.getPhoneOneToMany()
                            .stream()
                            .map(PhoneOneToMany::getPhone)
                            .collect(Collectors.toList());
                    dto.setPhone(phoneNumbers);

                    return dto;
                })
                .collect(Collectors.toSet());
    }

    //**번호로 찾기**
    public Set<PersonDTO> searchPhonePersonOnly(String phone) {
        Set<PersonOnly> personOnlies = personOnlyRepository.searchByPhone(phone);

        return personOnlies.stream()
                .map(person -> {
                    PersonDTO dto = new PersonDTO();
                    dto.setName(person.getName());
                    dto.setPhone(person.getPhone());
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    public Set<PersonDTO> searchPersonOneToOneByPhone(String phone) {
        Set<PersonOneToOne> personOneToOnes = personOneToOneRepository.searchByPhone(phone);

        return personOneToOnes.stream()
                .map(person -> {
                    PersonDTO dto = new PersonDTO();
                    dto.setName(person.getName());
                    dto.setPhone(person.getPhoneOneToOne().getPhone());
                    return dto;
                })
                .collect(Collectors.toSet());
    }

    public Set<PersonOneToManyDTO> searchPersonOneToManyByPhone(String phone){
        Set<PersonOneToMany> personOneToManies = personOneToManyRepository.searchByPhone(phone);

        return getPersonOneToManyDTOS(personOneToManies.stream(), personOneToManies);
    }

    private Set<PersonOneToManyDTO> getPersonOneToManyDTOS(Stream<PersonOneToMany> stream, Set<PersonOneToMany> personOneToManies) {
        return stream
                .map(person -> {
                    PersonOneToManyDTO dto = new PersonOneToManyDTO();
                    dto.setName(person.getName());
                    dto.setPhone(person.getPhoneOneToMany()
                            .stream()
                            .map(PhoneOneToMany::getPhone)
                            .collect(Collectors.toList()));

                    return dto;
                })
                .collect(Collectors.toSet());
    }
}
