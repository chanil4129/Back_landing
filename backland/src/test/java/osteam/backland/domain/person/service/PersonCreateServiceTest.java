package osteam.backland.domain.person.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonCreateServiceTest {
    @Mock
    private PersonOnlyRepository personOnlyRepository;

    @Mock
    private PersonOneToOneRepository personOneToOneRepository;

    @Mock
    private PersonOneToManyRepository personOneToManyRepository;

    @Mock
    private PersonUpdateService personUpdateService;

    @Mock
    private PersonValidationService personValidationService;

    @InjectMocks
    private PersonCreateService personCreateService;

    @Nested
    @DisplayName("성공")
    class success {
        @Test
        public void oneToOne() throws Exception{
            // given
            PersonDTO mockPersonDTO = new PersonDTO("John", "01011112222");

            // when
            PersonDTO result = personCreateService.oneToOne(mockPersonDTO);

            // then
            assertThat(result.getName()).isEqualTo(mockPersonDTO.getName());
            assertThat(result.getPhone()).isEqualTo(mockPersonDTO.getPhone());
            verify(personOneToOneRepository, times(1)).save(any(PersonOneToOne.class));
        }

        @Test
        public void oneToMany() throws Exception{
            // given
            PersonDTO mockPersonDTO = new PersonDTO("John", "01011112222");

            // when
            PersonDTO result = personCreateService.oneToMany(mockPersonDTO);

            // then
            assertThat(result.getName()).isEqualTo(mockPersonDTO.getName());
            assertThat(result.getPhone()).isEqualTo(mockPersonDTO.getPhone());
            verify(personOneToManyRepository, times(1)).save(any(PersonOneToMany.class));
        }

        @Test
        public void one() throws Exception{
            // given
            PersonDTO mockPersonDTO = new PersonDTO("John", "01011112222");

            // when
            PersonDTO result = personCreateService.one(mockPersonDTO);

            // then
            assertThat(result.getName()).isEqualTo(mockPersonDTO.getName());
            assertThat(result.getPhone()).isEqualTo(mockPersonDTO.getPhone());
            verify(personOnlyRepository, times(1)).save(any(PersonOnly.class));
        }
    }
}