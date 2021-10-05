package kr.sproutfx.platform.authservice.common.exception;

import org.springframework.http.HttpStatus;

import kr.sproutfx.platform.authservice.common.enumeration.ErrorStatus;
import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
    private final ErrorStatus errorStatus = ErrorStatus.UNAUTHORIZED;
    private final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
}
