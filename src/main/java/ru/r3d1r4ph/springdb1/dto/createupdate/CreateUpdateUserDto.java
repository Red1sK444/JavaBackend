package ru.r3d1r4ph.springdb1.dto.createupdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.r3d1r4ph.springdb1.entity.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateUpdateUserDto {

    @NotBlank(message = "FullName is mandatory")
    private String fullName;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotNull(message = "Role is mandatory")
    private Role role;
}
