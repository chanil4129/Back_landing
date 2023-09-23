package osteam.backland.domain.person.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.global.entity.PrimaryKeyEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class PersonOneToMany extends PrimaryKeyEntity {

    private String name;

    @OneToMany(
            mappedBy = "personOneToMany",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<PhoneOneToMany> phoneOneToMany = new HashSet<>();

    public PersonOneToMany(String name, Set<PhoneOneToMany> phones) {
        this.name = name;
        this.phoneOneToMany = phones;
        for (PhoneOneToMany phone : phones) {
            phone.updatePersonOneToMany(this);
        }
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void addPhoneOneToMany(PhoneOneToMany phoneOTM) {
        phoneOneToMany.add(phoneOTM);
        phoneOTM.updatePersonOneToMany(this);
    }
}
