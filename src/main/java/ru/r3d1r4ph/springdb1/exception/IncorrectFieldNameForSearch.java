package ru.r3d1r4ph.springdb1.exception;

import ru.r3d1r4ph.springdb1.exception.template.BadRequestException;

public class IncorrectFieldNameForSearch extends BadRequestException {
    public IncorrectFieldNameForSearch(String message) {
        super(message);
    }
}
