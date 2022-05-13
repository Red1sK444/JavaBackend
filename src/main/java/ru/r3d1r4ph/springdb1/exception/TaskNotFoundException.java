package ru.r3d1r4ph.springdb1.exception;

import ru.r3d1r4ph.springdb1.exception.template.NotFoundException;

public class TaskNotFoundException extends NotFoundException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
