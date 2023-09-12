package osteam.backland.domain.person.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonUpdateService {
    private final PersonOnlyRepository personOnlyRepository;
    private final PersonOneToOneRepository personOneToOneRepository;
    private final PersonOneToManyRepository personOneToManyRepository;

    public void updatePersonOnlyByName(PersonDTO personDTO, String newName) {
        personOnlyRepository.updateName(personDTO.getPhone(), newName);
    }

    public void updatePersonOneToOne(PersonDTO personDTO, String newName) {
        personOneToOneRepository.updateName(personDTO.getPhone(), newName);
    }

    public void updatePersonOneToMany(PersonDTO personDTO, String newName) {
        personOneToManyRepository.updateName((personDTO.getPhone()), newName);
    }
}
