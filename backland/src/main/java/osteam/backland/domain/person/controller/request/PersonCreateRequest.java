package osteam.backland.domain.person.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCreateRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 30)
    @Schema(description = "이름")
    private String name;

    @NotBlank(message = "Name is required")
    @Size(min = 10, max = 15)
    @Pattern(regexp = "^[0-9]+$", message = "Phone 번호를 입력하세요")
    @Schema(description = "번호")
    private String phone;
}
