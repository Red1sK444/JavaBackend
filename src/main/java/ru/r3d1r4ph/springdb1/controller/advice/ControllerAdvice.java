package ru.r3d1r4ph.springdb1.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.r3d1r4ph.springdb1.dto.response.ErrorDto;
import ru.r3d1r4ph.springdb1.exception.template.BadRequestException;
import ru.r3d1r4ph.springdb1.exception.template.ForbiddenException;
import ru.r3d1r4ph.springdb1.exception.template.NotFoundException;
import ru.r3d1r4ph.springdb1.exception.template.UnauthorizedException;

import java.util.Date;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFound(NotFoundException ex) {
        var body = ErrorDto.create(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleNotFound(BadRequestException ex) {
        var body = ErrorDto.create(new Date(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDto> handleNotFound(UnauthorizedException ex) {
        var body = ErrorDto.create(new Date(), HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorDto> handleNotFound(ForbiddenException ex) {
        var body = ErrorDto.create(new Date(), HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDto> handleNotFound(AuthenticationException ex) {
        var body = ErrorDto.create(new Date(), HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDto> handleNotFound(ResponseStatusException ex) {
        var body = ErrorDto.create(new Date(), ex.getRawStatusCode(), ex.getMessage());
        return ResponseEntity.status(ex.getRawStatusCode()).body(body);
    }
}
