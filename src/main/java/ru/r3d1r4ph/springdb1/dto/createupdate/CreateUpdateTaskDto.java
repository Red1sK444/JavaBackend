package ru.r3d1r4ph.springdb1.dto.createupdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.r3d1r4ph.springdb1.entity.Priority;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class CreateUpdateTaskDto {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "CreatorId is mandatory")
    private String creatorId;

    @NotBlank(message = "ExecutorId is mandatory")
    private String executorId;

    @NotNull(message = "Priority is mandatory")
    private Priority priority;

    @NotBlank(message = "ProjectId is mandatory")
    private String projectId;

    @NotNull(message = "TimeEstimate is mandatory")
    private Date timeEstimate;
}
