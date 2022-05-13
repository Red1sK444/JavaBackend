package ru.r3d1r4ph.springdb1.exception;

import ru.r3d1r4ph.springdb1.exception.template.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
