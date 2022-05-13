package ru.r3d1r4ph.springdb1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    private String id;

    private Date createDate;

    private Date editDate;

    private String name;

    private String description;

    private List<TaskDto> tasks;
}
