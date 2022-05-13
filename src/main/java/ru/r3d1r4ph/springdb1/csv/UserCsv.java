package ru.r3d1r4ph.springdb1.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import ru.r3d1r4ph.springdb1.entity.Role;
import ru.r3d1r4ph.springdb1.dto.converter.RoleConverter;

import java.util.Date;

@Getter
public class UserCsv {

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
    private String fullName;

    @CsvBindByPosition(position = 4)
    private String email;

    @CsvBindByPosition(position = 5)
    private String password;

    @CsvCustomBindByPosition(position = 6, converter = RoleConverter.class)
    private Role role;
}
