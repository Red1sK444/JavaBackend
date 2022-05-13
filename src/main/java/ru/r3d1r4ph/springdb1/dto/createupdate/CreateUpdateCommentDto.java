package ru.r3d1r4ph.springdb1.dto.createupdate;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateUpdateCommentDto {

    @NotBlank(message = "UserId is mandatory")
    private String userId;

    @NotNull(message = "TaskIds is mandatory")
    private List<String> taskIds;

    @NotBlank(message = "Text is mandatory")
    private String text;
}
