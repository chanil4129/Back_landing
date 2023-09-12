package osteam.backland.global.entity.exception.controller.response;

import lombok.Data;

@Data
public class CommonExceptionResponse {
    private final String code;
    private final String message;
}
