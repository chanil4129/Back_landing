package osteam.backland.domain.phone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.global.entity.PrimaryKeyEntity;

@Entity
@Getter
@NoArgsConstructor
public class PhoneOneToMany extends PrimaryKeyEntity {

    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "fk_person_id",
            referencedColumnName = "id"
    )
    private PersonOneToMany personOneToMany;

    public PhoneOneToMany(String phone, PersonOneToMany personOneToMany) {
        this.phone = phone;
        updatePersonOneToMany(personOneToMany);
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    public void updatePersonOneToMany(PersonOneToMany personOneToMany) {
        this.personOneToMany = personOneToMany;
    }
}
