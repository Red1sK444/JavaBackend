package ru.r3d1r4ph.springdb1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.r3d1r4ph.springdb1.dto.response.MessageDto;

@Service
@RequiredArgsConstructor
public class CsvService {

    private final CommentService commentService;

    private final ProjectService projectService;

    private final TaskService taskService;

    private final UserService userService;

    @Transactional
    public MessageDto load() {

        userService.loadUsersFromCsv();
        projectService.loadProjectsFromCsv();
        taskService.loadTasksFromCsv();
        commentService.loadCommentsFromCsv();

        return new MessageDto("OK");
    }
}
