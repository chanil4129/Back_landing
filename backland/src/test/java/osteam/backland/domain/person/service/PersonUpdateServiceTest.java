package osteam.backland.domain.person.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonUpdateServiceTest {
    @Mock
    private PersonOnlyRepository personOnlyRepository;

    @Mock
    private PersonOneToOneRepository personOneToOneRepository;

    @Mock
    private PersonOneToManyRepository personOneToManyRepository;

    @InjectMocks
    private PersonUpdateService personUpdateService;

    @Nested
    @DisplayName("업데이트")
    class update {
        @Test
        public void updateNamePersonOnly() throws Exception {
            //given
            PersonDTO personDTO = PersonDTO.builder()
                    .name("kim")
                    .phone("01012345678")
                    .build();

            // when
            personUpdateService.updateNamePersonOnly(personDTO);

            // then
            verify(personOnlyRepository, times(1)).updateName(personDTO.getPhone(), personDTO.getName());
        }

        @Test
        public void updateNamePersonOneToOne() throws Exception {
            //given
            PersonDTO personDTO = PersonDTO.builder()
                    .name("kim")
                    .phone("01012345678")
                    .build();

            //when
            personUpdateService.updateNamePersonOneToOne(personDTO);

            //then
            verify(personOneToOneRepository, times(1)).updateName(personDTO.getPhone(), personDTO.getName());
        }

        @Test
        public void updateNamePersonOneToMany() throws Exception {
            //given
            PersonDTO personDTO = PersonDTO.builder()
                    .name("kim")
                    .phone("01012345678")
                    .build();

            //when
            personUpdateService.updateNamePersonOneToMany(personDTO);

            //then
            verify(personOneToManyRepository, times(1)).updateName(personDTO.getPhone(), personDTO.getName());
        }
    }
}