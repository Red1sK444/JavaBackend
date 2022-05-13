package ru.r3d1r4ph.springdb1.exception.template;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ForbiddenException extends ResponseStatusException {

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
