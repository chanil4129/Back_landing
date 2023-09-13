package osteam.backland.domain.person.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.entity.dto.PersonOneToManyDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonBooksResponse {
    private List<PersonResponse> personOnly;
    private List<PersonResponse> personOneToOne;
    private List<PersonResponse> personOneToMany;

    public void updatePersonOnly(Set<PersonDTO> personOnlyDTOS) {
        this.personOnly = personOnlyDTOS.stream()
                .map(dto -> new PersonResponse(dto.getName(), dto.getPhone()))
                .collect(Collectors.toList());
    }

    public void updatePersonOneToOne(Set<PersonDTO> personOneToOneDTOS) {
        this.personOneToOne = personOneToOneDTOS.stream()
                .map(dto -> new PersonResponse(dto.getName(), dto.getPhone()))
                .collect(Collectors.toList());
    }

    public void updatePersonOneToMany(Set<PersonOneToManyDTO> personOneToManyDTOS) {
        this.personOneToMany = personOneToManyDTOS.stream()
                .flatMap(dto -> dto.getPhone().stream()
                        .map(phone -> new PersonResponse(dto.getName(), phone)))
                .collect(Collectors.toList());
    }
}
