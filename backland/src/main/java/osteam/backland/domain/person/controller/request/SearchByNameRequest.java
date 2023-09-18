package osteam.backland.domain.person.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchByNameRequest {
    @NotNull
    @Size(min = 1, max = 30)
    @Schema(description = "이름")
    private String name;
}
