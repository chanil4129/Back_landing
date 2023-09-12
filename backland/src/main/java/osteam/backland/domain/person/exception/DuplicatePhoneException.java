package osteam.backland.domain.person.exception;

import org.springframework.http.HttpStatus;
import osteam.backland.global.entity.exception.CommonException;

public class DuplicatePhoneException extends CommonException {
    public DuplicatePhoneException() {
        super("PHONE_DUPLICATE","핸드폰 번호가 중복입니다.",HttpStatus.CONFLICT);
    }
}
