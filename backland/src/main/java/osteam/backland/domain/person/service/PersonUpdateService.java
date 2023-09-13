package osteam.backland.domain.person.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PersonUpdateService {
    private final PersonOnlyRepository personOnlyRepository;
    private final PersonOneToOneRepository personOneToOneRepository;
    private final PersonOneToManyRepository personOneToManyRepository;

    public void updateNamePersonOnly(PersonDTO personDTO) {
        personOnlyRepository.updateName(personDTO.getPhone(), personDTO.getName());
    }

    public void updateNamePersonOneToOne(PersonDTO personDTO) {
        personOneToOneRepository.updateName(personDTO.getPhone(), personDTO.getName());
    }

    public void updateNamePersonOneToMany(PersonDTO personDTO) {
        personOneToManyRepository.updateName((personDTO.getPhone()), personDTO.getName());
    }
}
