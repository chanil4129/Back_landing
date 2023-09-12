package osteam.backland.global.entity.exception.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import osteam.backland.global.entity.exception.CommonException;
import osteam.backland.global.entity.exception.controller.response.CommonExceptionResponse;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonExceptionResponse> commonExceptionHandler(CommonException e) {
        return new ResponseEntity<>(
                new CommonExceptionResponse(
                        e.getCode(),
                        e.getMessage()
                ),
                e.getStatus()
        );
    }
}
