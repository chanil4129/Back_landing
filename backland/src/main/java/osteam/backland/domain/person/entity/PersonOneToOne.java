package osteam.backland.domain.person.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import osteam.backland.domain.phone.entity.PhoneOneToOne;
import osteam.backland.global.entity.PrimaryKeyEntity;


@Entity
@Getter
@NoArgsConstructor
public class PersonOneToOne extends PrimaryKeyEntity {

    private String name;

    @OneToOne(
            mappedBy = "personOneToOne",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private PhoneOneToOne phoneOneToOne;

    public PersonOneToOne(String name, PhoneOneToOne phoneOneToOne) {
        this.name = name;
        updatePhoneOneToOne(phoneOneToOne);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePhoneOneToOne(PhoneOneToOne phoneOTO) {
        phoneOneToOne=phoneOTO;
        phoneOTO.updatePersonOneToOne(this);
    }
}
