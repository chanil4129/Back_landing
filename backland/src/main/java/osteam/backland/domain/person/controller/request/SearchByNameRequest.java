package osteam.backland.domain.person.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchByNameRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 30)
    @Schema(description = "이름")
    private String name;
}
