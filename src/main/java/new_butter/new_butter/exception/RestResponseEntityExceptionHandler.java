package new_butter.new_butter.exception;

import new_butter.new_butter.exception.api_exception.ApiMessageException;
import new_butter.new_butter.exception.reponse_exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {
    @ExceptionHandler(value = { ApiMessageException.class })
    protected ResponseEntity<Object> handleSignupException(ApiMessageException ex) {
        ResponseException signupException = new ResponseException(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(signupException);
    }
}