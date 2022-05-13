package ru.r3d1r4ph.springdb1.dto.createupdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.r3d1r4ph.springdb1.entity.Priority;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePriorityDto {

    @NotNull(message = "Priority is mandatory")
    private Priority priority;
}
