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
import osteam.backland.domain.person.entity.dto.PersonOneToManyDTO;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.entity.PhoneOneToOne;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidCheckServiceTest {
    @Mock
    private PersonOnlyRepository personOnlyRepository;

    @Mock
    private PersonOneToOneRepository personOneToOneRepository;

    @Mock
    private PersonOneToManyRepository personOneToManyRepository;

    @InjectMocks
    private ValidCheckService validCheckService;

    @Nested
    @DisplayName("중복 검사")
    class duplicate{
        @Nested
        @DisplayName("중복 발견")
        class success{
            @Test
            public void duplicatePersonOnly() throws Exception {
                //given
                String phone = "01012345678";
                PersonOnly mockPerson = new PersonOnly("Test",phone);
                given(personOnlyRepository.searchByPhone(phone)).willReturn(Optional.of(mockPerson));

                //when
                Optional<PersonDTO> result = validCheckService.duplicatePersonOnly(phone);

                //then
                assertThat(result).isPresent();
                assertThat(result.get().getName()).isEqualTo("Test");
                assertThat(result.get().getPhone()).isEqualTo(phone);
            }

            @Test
            public void duplicatePersonOneToOne() throws Exception {
                //given
                String phone = "01012345678";
                PhoneOneToOne mockPhone = new PhoneOneToOne(phone, null); // Create phone without PersonOneToOne first
                PersonOneToOne mockPerson = new PersonOneToOne("Test", mockPhone); // Then, associate it with PersonOneToOne
                given(personOneToOneRepository.searchByPhone(phone)).willReturn(Optional.of(mockPerson));

                //when
                Optional<PersonDTO> result = validCheckService.duplicatePersonOneToOne(phone);

                //then
                assertThat(result).isPresent();
                assertThat(result.get().getName()).isEqualTo("Test");
                assertThat(result.get().getPhone()).isEqualTo(phone);
            }

            @Test
            public void duplicatePersonOneToMany() throws Exception {
                //given
                String phone = "01012345678";
                PhoneOneToMany mockPhone = new PhoneOneToMany(phone, null); // Same strategy here
                PersonOneToMany mockPerson = new PersonOneToMany("Test", Set.of(mockPhone));
                given(personOneToManyRepository.searchByPhone(phone)).willReturn(Optional.of(mockPerson));

                //when
                Optional<PersonOneToManyDTO> result = validCheckService.duplicatePersonOneToMany(phone);

                //then
                assertThat(result).isPresent();
                assertThat(result.get().getName()).isEqualTo("Test");
                assertThat(result.get().getPhone()).containsExactly(phone);
            }
        }
        @Nested
        @DisplayName("중복 없음")
        class fail{
            @Test
            public void duplicatePersonOnly_NotFound() {
                //given
                String phone = "01012345678";
                given(personOnlyRepository.searchByPhone(phone)).willReturn(Optional.empty());

                //when
                Optional<PersonDTO> result = validCheckService.duplicatePersonOnly(phone);  // Change the method here

                //then
                assertThat(result).isEmpty();
            }

            @Test
            public void duplicatePersonOneToOne_NotFound() {
                //given
                String phone = "01012345678";
                given(personOneToOneRepository.searchByPhone(phone)).willReturn(Optional.empty());

                //when
                Optional<PersonDTO> result = validCheckService.duplicatePersonOneToOne(phone);  // Change the method here

                //then
                assertThat(result).isEmpty();
            }

            @Test
            public void duplicatePersonOneToMany_NotFound() {
                //given
                String phone = "01012345678";
                given(personOneToManyRepository.searchByPhone(phone)).willReturn(Optional.empty());

                //when
                Optional<PersonOneToManyDTO> result = validCheckService.duplicatePersonOneToMany(phone);

                //then
                assertThat(result).isEmpty();
            }
        }
    }


}