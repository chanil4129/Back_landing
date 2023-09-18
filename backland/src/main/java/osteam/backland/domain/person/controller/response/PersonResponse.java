package osteam.backland.domain.person.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Data
public class PersonResponse {

    @NonNull
    @Schema(description = "이름")
    private String name;

    @NonNull
    @Schema(description = "번호")
    private String phone;
}
