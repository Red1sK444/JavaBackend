package ru.r3d1r4ph.springdb1.dto.search;

import lombok.Data;

import java.util.Map;

@Data
public class FetchDto {
    private Map<String, String> fields;
}
