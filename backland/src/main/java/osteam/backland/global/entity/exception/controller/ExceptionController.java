package osteam.backland.global.entity.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonExceptionResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return new CommonExceptionResponse(
                "INVALID_REQUEST",
                e.getBindingResult().getFieldError().getDefaultMessage()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonExceptionResponse httpMessageNotSearchableExceptionHandler(HttpMessageNotReadableException e) {
        return new CommonExceptionResponse(
                "INVALID_JSON",
                "JSON 형식이 잘못되었습니다."
        );
    }
}
