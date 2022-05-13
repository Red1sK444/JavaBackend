package ru.r3d1r4ph.springdb1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.r3d1r4ph.springdb1.entity.Priority;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private String id;

    private Date createDate;

    private Date editDate;

    private String title;

    private String description;

    private String creator;

    private String executor;

    private Priority priority;

    private String project;

    private Date timeEstimate;

    private List<CommentDto> comments;
}
