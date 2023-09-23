package osteam.backland.domain.phone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.global.entity.PrimaryKeyEntity;

@Entity
@Getter
@NoArgsConstructor
@Builder(toBuilder = true)
public class PhoneOneToOne extends PrimaryKeyEntity {

    private String phone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private PersonOneToOne personOneToOne;

    public PhoneOneToOne(String phone, PersonOneToOne personOneToOne) {
        this.phone=phone;
        updatePersonOneToOne(personOneToOne);
    }

    public void updatePersonOneToOne(PersonOneToOne personOneToOne) {
        this.personOneToOne = personOneToOne;
    }
}
