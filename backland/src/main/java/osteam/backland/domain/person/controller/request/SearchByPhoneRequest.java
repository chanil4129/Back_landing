package osteam.backland.domain.person.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchByPhoneRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 10, max = 15)
    @Pattern(regexp = "^[0-9]+$", message = "Phone 번호를 입력하세요")
    @Schema(description = "번호")
    private String phone;
}
