package ru.r3d1r4ph.springdb1.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor(staticName = "create")
public class ErrorDto {

    private final Date timestamp;

    private final Integer status;

    private final String message;

}
