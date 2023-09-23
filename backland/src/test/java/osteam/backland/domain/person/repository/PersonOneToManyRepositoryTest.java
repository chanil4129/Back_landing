package osteam.backland.domain.person.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.phone.entity.PhoneOneToMany;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class PersonOneToManyRepositoryTest {
    @Autowired
    private PersonOneToManyRepository personOneToManyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional(readOnly = true)
    public void searchByPhoneContaining() throws Exception{
        //given
        PhoneOneToMany phoneOneToMany1 = PhoneOneToMany.builder().phone("01022223333").build();
        PhoneOneToMany phoneOneToMany2 = PhoneOneToMany.builder().phone("01044445555").build();
        PersonOneToMany personOneToMany = PersonOneToMany.builder()
                .name("kim")
                .phoneOneToMany(new HashSet<>(Arrays.asList(phoneOneToMany1, phoneOneToMany2)))
                .build();
        personOneToManyRepository.save(personOneToMany);
        entityManager.flush();
        entityManager.clear();
        //when
        Set<PersonOneToMany> searched = personOneToManyRepository.searchByPhoneContaining("010");
        //then
        assertThat(searched).hasSize(1);
        assertThat(searched.iterator().next()).usingRecursiveComparison().isEqualTo(personOneToMany);
    }

    @Test
    @Transactional(readOnly = true)
    public void searchByPhone() throws Exception{
        //given
        PhoneOneToMany phoneOneToMany1 = PhoneOneToMany.builder().phone("01022223333").build();
        PhoneOneToMany phoneOneToMany2 = PhoneOneToMany.builder().phone("01044445555").build();
        PersonOneToMany personOneToMany = PersonOneToMany.builder()
                .name("kim")
                .phoneOneToMany(new HashSet<>(Arrays.asList(phoneOneToMany1, phoneOneToMany2)))
                .build();
        personOneToManyRepository.save(personOneToMany);
        entityManager.flush();
        entityManager.clear();
        //when
        Optional<PersonOneToMany> searched = personOneToManyRepository.searchByPhone("01022223333");
        //then
        assertThat(searched.isPresent()).isTrue();
        assertThat(searched.get()).usingRecursiveComparison().isEqualTo(personOneToMany);
    }

    @Test
    @Transactional
    public void updateName() throws Exception{
        //given
        PhoneOneToMany phoneOneToMany1 = PhoneOneToMany.builder().phone("01022223333").build();
        PhoneOneToMany phoneOneToMany2 = PhoneOneToMany.builder().phone("01044445555").build();
        PersonOneToMany personOneToMany = PersonOneToMany.builder()
                .name("kim")
                .phoneOneToMany(new HashSet<>(Arrays.asList(phoneOneToMany1, phoneOneToMany2)))
                .build();
        personOneToManyRepository.save(personOneToMany);
        //when
        PersonOneToMany updatedPerson = personOneToMany.toBuilder().name("lee").build();
        personOneToManyRepository.save(updatedPerson);
        entityManager.flush();
        entityManager.clear();
        //then
        Optional<PersonOneToMany> searched = personOneToManyRepository.searchByPhone("01022223333");
        assertThat(searched.isPresent()).isTrue();
        assertThat(searched.get().getName()).isEqualTo("lee");
        Set<String> phoneNumbers = searched.get().getPhoneOneToMany().stream()
                .map(PhoneOneToMany::getPhone)
                .collect(Collectors.toSet());
        assertThat(phoneNumbers).contains("01022223333", "01044445555");
    }
}