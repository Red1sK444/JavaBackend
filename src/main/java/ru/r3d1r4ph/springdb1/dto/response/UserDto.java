package ru.r3d1r4ph.springdb1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.r3d1r4ph.springdb1.entity.Role;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;

    private Date createDate;

    private Date editDate;

    private String fullName;

    private String email;

    private String password;

    private Role role;

    private List<CommentDto> comments;

    private List<TaskDto> creatorTasks;

    private List<TaskDto> executorTasks;
}
