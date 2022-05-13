package ru.r3d1r4ph.springdb1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.r3d1r4ph.springdb1.dto.response.MessageDto;
import ru.r3d1r4ph.springdb1.service.CsvService;

@RestController
@RequestMapping("/csv")
@RequiredArgsConstructor
public class CsvController {

    private final CsvService csvService;

    @PostMapping
    public MessageDto loadCsv() {
        return csvService.load();
    }
}
