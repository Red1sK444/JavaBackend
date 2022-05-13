package ru.r3d1r4ph.springdb1.exception;

import ru.r3d1r4ph.springdb1.exception.template.NotFoundException;

public class ProjectNotFoundException extends NotFoundException {

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
