package osteam.backland.domain.person.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.global.entity.PrimaryKeyEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Builder(toBuilder = true)
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
        this.phoneOneToMany = new HashSet<>(phones);
        for (PhoneOneToMany phone : this.phoneOneToMany) {
            phone.updatePersonOneToMany(this);
        }
    }

    public void addPhoneOneToMany(PhoneOneToMany phoneOTM) {
        if (this.phoneOneToMany == null) {
            this.phoneOneToMany = new HashSet<>();
        }
        phoneOneToMany.add(phoneOTM);
        phoneOTM.updatePersonOneToMany(this);
    }
}
