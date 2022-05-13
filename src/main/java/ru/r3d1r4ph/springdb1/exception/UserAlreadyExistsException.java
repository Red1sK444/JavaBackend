package ru.r3d1r4ph.springdb1.exception;

import ru.r3d1r4ph.springdb1.exception.template.BadRequestException;

public class UserAlreadyExistsException extends BadRequestException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
