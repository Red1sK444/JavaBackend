package ru.r3d1r4ph.springdb1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateTaskDto;
import ru.r3d1r4ph.springdb1.dto.createupdate.UpdateExecutorDto;
import ru.r3d1r4ph.springdb1.dto.createupdate.UpdatePriorityDto;
import ru.r3d1r4ph.springdb1.dto.response.MessageDto;
import ru.r3d1r4ph.springdb1.dto.search.FetchDto;
import ru.r3d1r4ph.springdb1.dto.search.SearchTasksByCommentTextDto;
import ru.r3d1r4ph.springdb1.dto.response.TaskDto;
import ru.r3d1r4ph.springdb1.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskDto createTask(@Validated @RequestBody CreateUpdateTaskDto createUpdateTaskDto) {
        return taskService.createTask(createUpdateTaskDto);
    }

    @GetMapping(value = "/{id}")
    public TaskDto getTask(@PathVariable UUID id) {
        return taskService.getTaskDtoById(id.toString());
    }

    @GetMapping(value = "/executor/{executorId}")
    public List<TaskDto> getTasksByExecutor(@PathVariable UUID executorId) {
        return taskService.getTaskDtosByExecutor(executorId.toString());
    }

    @GetMapping(value = "/comment")
    public List<TaskDto> getTasksByCommentText(@RequestBody SearchTasksByCommentTextDto searchTasksByCommentTextDto) {
        return taskService.getTasksDtoByCommentText(searchTasksByCommentTextDto);
    }

    @GetMapping(value = "/fetch")
    public Page<TaskDto> fetchTasks(Pageable pageable, @RequestBody FetchDto dto) {
        return taskService.getTaskDtosByAllExceptTimeEstimateAndComments(pageable, dto);
    }

    @PatchMapping("/{id}/priority")
    public MessageDto changePriority(@PathVariable UUID id, @RequestBody UpdatePriorityDto updatePriorityDto) {
        return taskService.changePriority(id.toString(), updatePriorityDto);
    }

    @PatchMapping("/{id}/executor")
    public MessageDto changeExecutor(@PathVariable UUID id, @RequestBody UpdateExecutorDto updateExecutorDto) {
        return taskService.changeExecutor(id.toString(), updateExecutorDto);
    }

    @PutMapping(value = "/{id}")
    public TaskDto updateTask(@PathVariable UUID id, @Validated @RequestBody CreateUpdateTaskDto createUpdateTaskDto) {
        return taskService.updateTask(id.toString(), createUpdateTaskDto);
    }

    @DeleteMapping(value = "/{id}")
    public MessageDto deleteTask(@PathVariable UUID id) {
        return taskService.deleteTask(id.toString());
    }
}
