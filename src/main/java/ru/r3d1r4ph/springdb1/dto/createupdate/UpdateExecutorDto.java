package ru.r3d1r4ph.springdb1.dto.createupdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExecutorDto {

    @NotBlank(message = "ExecutorId is mandatory")
    private String executorId;
}
