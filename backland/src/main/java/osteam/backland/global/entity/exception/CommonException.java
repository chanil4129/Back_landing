package osteam.backland.global.entity.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CommonException extends RuntimeException {
    private final String code;
    private final String message;
    private final HttpStatus status;
}
