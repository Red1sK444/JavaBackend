package ru.r3d1r4ph.springdb1.exception;

import ru.r3d1r4ph.springdb1.exception.template.NotFoundException;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
