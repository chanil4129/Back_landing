package osteam.backland.domain.person.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.phone.entity.PhoneOneToMany;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class PersonOneToManyRepositoryTest {
    @Autowired
    private PersonOneToManyRepository personOneToManyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional(readOnly = true)
    public void PersonOneToOneSave() throws Exception{
        //given
        PersonOneToMany personOneToMany=new PersonOneToMany();
        PhoneOneToMany phoneOneToMany1 = new PhoneOneToMany("01022223333", null);
        PhoneOneToMany phoneOneToMany2 = new PhoneOneToMany("01044445555", null);
        personOneToMany.updateName("kim");
        personOneToMany.addPhoneOneToMany(phoneOneToMany1);
        personOneToMany.addPhoneOneToMany(phoneOneToMany2);
        //when
        PersonOneToMany savedPerson = personOneToManyRepository.save(personOneToMany);
        //then
        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo("kim");
        assertThat(savedPerson.getPhoneOneToMany()).contains(phoneOneToMany1, phoneOneToMany2);
    }

    @Test
    @Transactional(readOnly = true)
    public void searchByPhoneContaining() throws Exception{
        //given
        PersonOneToMany personOneToMany=new PersonOneToMany();
        PhoneOneToMany phoneOneToMany1 = new PhoneOneToMany("01022223333", null);
        PhoneOneToMany phoneOneToMany2 = new PhoneOneToMany("01044445555", null);
        personOneToMany.updateName("kim");
        personOneToMany.addPhoneOneToMany(phoneOneToMany1);
        personOneToMany.addPhoneOneToMany(phoneOneToMany2);
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
        PersonOneToMany personOneToMany=new PersonOneToMany();
        PhoneOneToMany phoneOneToMany1 = new PhoneOneToMany("01022223333", null);
        PhoneOneToMany phoneOneToMany2 = new PhoneOneToMany("01044445555", null);
        personOneToMany.updateName("kim");
        personOneToMany.addPhoneOneToMany(phoneOneToMany1);
        personOneToMany.addPhoneOneToMany(phoneOneToMany2);
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
        PersonOneToMany personOneToMany=new PersonOneToMany();
        PhoneOneToMany phoneOneToMany1 = new PhoneOneToMany("01022223333", null);
        PhoneOneToMany phoneOneToMany2 = new PhoneOneToMany("01044445555", null);
        personOneToMany.updateName("kim");
        personOneToMany.addPhoneOneToMany(phoneOneToMany1);
        personOneToMany.addPhoneOneToMany(phoneOneToMany2);
        personOneToManyRepository.save(personOneToMany);
        //when
        personOneToMany.updateName("lee");
        entityManager.flush();
        entityManager.clear();
        //then
        Optional<PersonOneToMany> searched = personOneToManyRepository.searchByPhone("01022223333");
        assertThat(searched.isPresent()).isTrue();
        assertThat(searched.get()).usingRecursiveComparison().isEqualTo(personOneToMany);
        assertThat(searched.get().getName()).isEqualTo("lee");
    }
}