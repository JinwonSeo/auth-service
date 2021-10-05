package kr.sproutfx.platform.authservice.common.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.sproutfx.platform.authservice.common.enumeration.ErrorStatus;
import kr.sproutfx.platform.authservice.common.exception.UnauthorizedException;
import kr.sproutfx.platform.authservice.common.response.Result;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Result<Object>> exception (final Throwable t) {
        return new ResponseEntity<Result<Object>>(new Result<>(ErrorStatus.UNHANDLED), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Result<Object>> unauthorizedException (final UnauthorizedException e) {
        return new ResponseEntity<Result<Object>>(new Result<>(e.getErrorStatus()), e.getHttpStatus());
    }
}