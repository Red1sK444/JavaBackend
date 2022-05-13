package ru.r3d1r4ph.springdb1.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import ru.r3d1r4ph.springdb1.dto.converter.PriorityConverter;
import ru.r3d1r4ph.springdb1.entity.Priority;

import java.util.Date;

@Getter
public class TaskCsv {

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
    private String title;

    @CsvBindByPosition(position = 4)
    private String description;

    @CsvBindByPosition(position = 5)
    private String creatorId;

    @CsvBindByPosition(position = 6)
    private String executorId;

    @CsvCustomBindByPosition(position = 7, converter = PriorityConverter.class)
    private Priority priority;

    @CsvBindByPosition(position = 8)
    private String projectId;

    @CsvDate(
            value = "dd.MM.yyyy HH:mm:ss",
            writeFormatEqualsReadFormat = false,
            writeFormat = "yyyy-MM-dd'T'HH:mm:ss"
    )
    @CsvBindByPosition(position = 9)
    private Date timeEstimate;
}
