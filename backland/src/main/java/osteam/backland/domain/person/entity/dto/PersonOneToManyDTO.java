package osteam.backland.domain.person.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonOneToManyDTO {
    private String name;
    private List<String> phone;
}
