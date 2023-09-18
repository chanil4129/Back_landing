package osteam.backland.domain.person.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchByPhoneRequest {
    @NotNull
    @Pattern(regexp = "^[0-9]+$", message = "Phone 번호를 입력하세요")
    @Schema(description = "번호")
    private String phone;
}
