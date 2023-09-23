package osteam.backland.domain.person.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import osteam.backland.domain.person.entity.PersonOnly;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
class PersonOnlyRepositoryTest {
    @Autowired
    private PersonOnlyRepository personOnlyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional(readOnly = true)
    public void PersonOnlySave() throws Exception{
        //given
        PersonOnly personOnly = getPersonOnly("test","01012345678");
        //when
        PersonOnly savedPerson = personOnlyRepository.save(personOnly);
        //then
        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo("test");
        assertThat(savedPerson.getPhone()).isEqualTo("01012345678");
    }

    @Test
    @Transactional(readOnly = true)
    public void searchByNameContaining() throws Exception{
        //given
        PersonOnly personOnly1 = getPersonOnly("test1", "01012345678");
        PersonOnly personOnly2 = getPersonOnly("test2", "01098765432");
        personOnlyRepository.save(personOnly1);
        personOnlyRepository.save(personOnly2);
        entityManager.flush();
        entityManager.clear();
        //when
        Set<PersonOnly> searched = personOnlyRepository.searchByNameContaining("test");
        //then
        assertThat(searched).hasSize(2);
        assertThat(searched).usingRecursiveFieldByFieldElementComparator().contains(personOnly1, personOnly2);
    }

    @Test
    @Transactional(readOnly = true)
    public void searchByPhoneContaining() throws Exception{
        //given
        PersonOnly personOnly1 = getPersonOnly("test1","01022223333");
        PersonOnly personOnly2 = getPersonOnly("test2", "010222244444");
        personOnlyRepository.save(personOnly1);
        personOnlyRepository.save(personOnly2);
        entityManager.flush();
        entityManager.clear();

        // when
        Set<PersonOnly> searched = personOnlyRepository.searchByNameContaining("test");

        // then
        assertThat(searched).hasSize(2);
        Optional<PersonOnly> fetchedPersonOnly1 = personOnlyRepository.findById(personOnly1.getId());
        Optional<PersonOnly> fetchedPersonOnly2 = personOnlyRepository.findById(personOnly2.getId());
        assertThat(searched).contains(fetchedPersonOnly1.get(), fetchedPersonOnly2.get());
    }

    @Test
    @Transactional(readOnly = true)
    public void searchByPhone() throws Exception{
        //given
        PersonOnly personOnly = getPersonOnly("test", "01012345678");
        personOnlyRepository.save(personOnly);
        entityManager.flush();
        entityManager.clear();
        //when
        Optional<PersonOnly> searched = personOnlyRepository.searchByPhone("01012345678");
        //then
        assertThat(searched.get()).usingRecursiveComparison().isEqualTo(personOnly);
    }

    @Test
    @Transactional
    public void updateName() throws Exception{
        //given
        PersonOnly personOnly = getPersonOnly("test","01012345678");
        personOnlyRepository.save(personOnly);
        //when
        personOnlyRepository.updateName("01012345678", "os");
        entityManager.flush();
        entityManager.clear();
        //then
        PersonOnly updatedPerson = personOnlyRepository.searchByPhone("01012345678").orElseThrow(() -> new IllegalArgumentException("Person not found"));
        assertThat(updatedPerson.getName()).isEqualTo("os");
    }

    private static PersonOnly getPersonOnly(String name, String phone) {
        PersonOnly personOnly = new PersonOnly();
        personOnly.updateName(name);
        personOnly.updatePhone(phone);
        return personOnly;
    }
}