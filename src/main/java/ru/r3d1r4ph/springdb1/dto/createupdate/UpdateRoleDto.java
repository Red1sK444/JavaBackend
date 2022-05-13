package ru.r3d1r4ph.springdb1.dto.createupdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.r3d1r4ph.springdb1.entity.Role;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDto {

    @NotNull(message = "Role is mandatory")
    private Role role;
}
