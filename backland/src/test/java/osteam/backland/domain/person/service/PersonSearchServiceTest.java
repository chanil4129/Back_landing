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
import osteam.backland.domain.phone.repository.PhoneOneToManyRepository;
import osteam.backland.domain.phone.repository.PhoneOneToOneRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PersonSearchServiceTest {
    @Mock
    private PersonOnlyRepository personOnlyRepository;

    @Mock
    private PersonOneToOneRepository personOneToOneRepository;

    @Mock
    private PersonOneToManyRepository personOneToManyRepository;

    @Mock
    private PhoneOneToOneRepository phoneOneToOneRepository;

    @Mock
    private PhoneOneToManyRepository phoneOneToManyRepository;

    @InjectMocks
    private PersonSearchService personSearchService;

    @Nested
    @DisplayName("전체 검사")
    class searchAll {
        @Test
        public void serachAllPersonOnly() throws Exception{
            //given
            List<PersonOnly> mockPersons = Arrays.asList(new PersonOnly("John", "01011112222"), new PersonOnly("Jane", "01033334444"));
            given(personOnlyRepository.findAll()).willReturn(mockPersons);

            //when
            Set<PersonDTO> result = personSearchService.searchAllPersonOnly();

            //then
            assertThat(result).hasSize(2);
            assertThat(result).extracting("name").containsExactlyInAnyOrder("John", "Jane");
        }

        @Test
        public void searchAllPersonOneToOne() throws Exception{
            //given
            List<PersonOneToOne> mockPersons = Arrays.asList(
                    new PersonOneToOne("John", new PhoneOneToOne("01011112222",null)),
                    new PersonOneToOne("Jane", new PhoneOneToOne("01033334444",null))
            );
            given(personOneToOneRepository.findAll()).willReturn(mockPersons);

            //when
            Set<PersonDTO> result = personSearchService.searchAllPersonOneToOne();

            //then
            assertThat(result).hasSize(2);
            assertThat(result.stream().anyMatch(dto -> dto.getName().equals("John") && dto.getPhone().equals("01011112222"))).isTrue();
            assertThat(result.stream().anyMatch(dto -> dto.getName().equals("Jane") && dto.getPhone().equals("01033334444"))).isTrue();
        }

        @Test
        public void searchAllPersonOneToMany() throws Exception{
            //given
            Set<PhoneOneToMany> phonesForJohn = Set.of(
                    new PhoneOneToMany("01011112222", null),
                    new PhoneOneToMany("01022223333", null)
            );
            Set<PhoneOneToMany> phonesForJane = Set.of(
                    new PhoneOneToMany("01033334444", null),
                    new PhoneOneToMany("01044445555", null)
            );

            PersonOneToMany personJohn = new PersonOneToMany("John", phonesForJohn);
            PersonOneToMany personJane = new PersonOneToMany("Jane", phonesForJane);

            List<PersonOneToMany> mockPersons = Arrays.asList(personJohn, personJane);

            given(personOneToManyRepository.findAll()).willReturn(mockPersons);

            //when
            Set<PersonOneToManyDTO> result = personSearchService.searchAllPersonOneToMany();

            //then
            assertThat(result).hasSize(2);
            assertThat(result.stream().anyMatch(dto -> dto.getName().equals("John") && dto.getPhone().contains("01011112222") && dto.getPhone().contains("01022223333"))).isTrue();
            assertThat(result.stream().anyMatch(dto -> dto.getName().equals("Jane") && dto.getPhone().contains("01033334444") && dto.getPhone().contains("01044445555"))).isTrue();
        }
    }

    @Nested
    @DisplayName("이름으로 찾기")
    class searchByName {
        @Test
        public void searchPersonOnlyByNameContaining() throws Exception{
            //given
            String serachName = "J";
            Set<PersonOnly> mockPersons = Set.of(new PersonOnly("John", "01011112222"), new PersonOnly("Jane", "01033334444"));
            given(personOnlyRepository.searchByNameContaining(serachName)).willReturn(mockPersons);

            //when
            Set<PersonDTO> result = personSearchService.searchPersonOnlyByNameContaining(serachName);

            //then
            assertThat(result).hasSize(2);
            assertThat(result.stream().anyMatch(dto -> dto.getName().equals("John") && dto.getPhone().equals("01011112222"))).isTrue();
            assertThat(result.stream().anyMatch(dto -> dto.getName().equals("Jane") && dto.getPhone().equals("01033334444"))).isTrue();
        }

        @Test
        public void searchPersonOneToOneByNameContaining() throws Exception{
            // given
            String searchName = "Jo";
            PhoneOneToOne phoneJohn = new PhoneOneToOne("01011112222", null);
            PersonOneToOne personJohn = new PersonOneToOne("John", phoneJohn);
            phoneJohn.updatePersonOneToOne(personJohn);

            Set<PhoneOneToOne> mockPhones = Set.of(phoneJohn);
            given(phoneOneToOneRepository.searchByNameContaining(searchName)).willReturn(mockPhones);

            // when
            Set<PersonDTO> result = personSearchService.searchPersonOneToOneByNameContaining(searchName);

            // then
            assertThat(result).hasSize(1);
            assertThat(result.iterator().next().getName()).isEqualTo("John");
            assertThat(result.iterator().next().getPhone()).isEqualTo("01011112222");
        }

        @Test
        public void searchPersonOneToManyByNameContaining() throws Exception{
            // given
            String searchName = "Jo";
            PhoneOneToMany phoneJohn1 = new PhoneOneToMany("01011112222", null);
            PhoneOneToMany phoneJohn2 = new PhoneOneToMany("01022223333", null);
            Set<PhoneOneToMany> phonesForJohn = Set.of(phoneJohn1, phoneJohn2);
            PersonOneToMany personJohn = new PersonOneToMany("John", phonesForJohn);

            phoneJohn1.updatePersonOneToMany(personJohn);
            phoneJohn2.updatePersonOneToMany(personJohn);

            Set<PhoneOneToMany> mockPersons = Set.of(phoneJohn1,phoneJohn2);
            given(phoneOneToManyRepository.searchByNameContaining(searchName)).willReturn(mockPersons);

            // when
            Set<PersonOneToManyDTO> result = personSearchService.searchPersonOneToManyByNameContaining(searchName);

            // then
            assertThat(result).hasSize(1);
            assertThat(result.iterator().next().getName()).isEqualTo("John");
            assertThat(result.iterator().next().getPhone()).containsExactlyInAnyOrder("01011112222", "01022223333");
        }
    }

    @Nested
    @DisplayName("번호로 찾기")
    class searchByPhone{
        @Test
        public void searchPersonOnlyByPhoneContaining() throws Exception{
            //given
            String serachPhone = "010";
            Set<PersonOnly> mockPersons = Set.of(new PersonOnly("John", "01011112222"), new PersonOnly("Jane", "01033334444"));
            given(personOnlyRepository.searchByPhoneContaining(serachPhone)).willReturn(mockPersons);

            //when
            Set<PersonDTO> result = personSearchService.searchPersonOnlyByPhoneContaining(serachPhone);

            //then
            assertThat(result).hasSize(2);
            assertThat(result.stream().anyMatch(dto -> dto.getName().equals("John") && dto.getPhone().equals("01011112222"))).isTrue();
            assertThat(result.stream().anyMatch(dto -> dto.getName().equals("Jane") && dto.getPhone().equals("01033334444"))).isTrue();
        }

        @Test
        public void searchPersonOneToOneByPhoneContaining() throws Exception{
            // given
            String searchPhone = "010";
            PhoneOneToOne phoneJohn = new PhoneOneToOne("01011112222", null);
            PersonOneToOne personJohn = new PersonOneToOne("John", phoneJohn);
            phoneJohn.updatePersonOneToOne(personJohn);

            Set<PersonOneToOne> mockPhones = Set.of(personJohn);
            given(personOneToOneRepository.searchByPhoneContaining(searchPhone)).willReturn(mockPhones);

            // when
            Set<PersonDTO> result = personSearchService.searchPersonOneToOneByPhoneContaining(searchPhone);

            // then
            assertThat(result).hasSize(1);
            assertThat(result.iterator().next().getName()).isEqualTo("John");
            assertThat(result.iterator().next().getPhone()).isEqualTo("01011112222");
        }

        @Test
        public void searchPersonOneToManyByPhoneContaining() throws Exception{
            // given
            String searchPhone = "010";
            PhoneOneToMany phoneJohn1 = new PhoneOneToMany("01011112222", null);
            PhoneOneToMany phoneJohn2 = new PhoneOneToMany("01022223333", null);
            Set<PhoneOneToMany> phonesForJohn = Set.of(phoneJohn1, phoneJohn2);
            PersonOneToMany personJohn = new PersonOneToMany("John", phonesForJohn);

            phoneJohn1.updatePersonOneToMany(personJohn);
            phoneJohn2.updatePersonOneToMany(personJohn);

            Set<PersonOneToMany> mockPersons = Set.of(personJohn);
            given(personOneToManyRepository.searchByPhoneContaining(searchPhone)).willReturn(mockPersons);

            // when
            Set<PersonOneToManyDTO> result = personSearchService.searchPersonOneToManyByPhoneContaining(searchPhone);

            // then
            assertThat(result).hasSize(1);
            assertThat(result.iterator().next().getName()).isEqualTo("John");
            assertThat(result.iterator().next().getPhone()).containsExactlyInAnyOrder("01011112222", "01022223333");
        }
    }
}