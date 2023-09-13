package osteam.backland.domain.person.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchByNameRequest {
    @NotNull
    @Size(min = 1, max = 30)
    private String name;
}
