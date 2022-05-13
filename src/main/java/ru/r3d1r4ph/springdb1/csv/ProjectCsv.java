package ru.r3d1r4ph.springdb1.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Getter;

import java.util.Date;

@Getter
public class ProjectCsv {

    @CsvBindByPosition(position = 0)
    private String id;

    @CsvDate(
            value = "dd.MM.yyyy",
            writeFormatEqualsReadFormat = false,
            writeFormat = "yyyy-MM-dd"
    )
    @CsvBindByPosition(position = 1)
    private Date createDate;

    @CsvDate(
            value = "dd.MM.yyyy",
            writeFormatEqualsReadFormat = false,
            writeFormat = "yyyy-MM-dd"
    )
    @CsvBindByPosition(position = 2)
    private Date editDate;

    @CsvBindByPosition(position = 3)
    private String name;

    @CsvBindByPosition(position = 4)
    private String description;
}
