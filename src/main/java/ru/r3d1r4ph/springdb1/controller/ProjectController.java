package ru.r3d1r4ph.springdb1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.r3d1r4ph.springdb1.dto.response.MessageDto;
import ru.r3d1r4ph.springdb1.dto.response.ProjectDto;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateProjectDto;
import ru.r3d1r4ph.springdb1.service.ProjectService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ProjectDto createProject(@Validated @RequestBody CreateUpdateProjectDto createUpdateProjectDto) {
        return projectService.createProject(createUpdateProjectDto);
    }

    @GetMapping(value = "/{id}")
    public ProjectDto getProject(@PathVariable UUID id) {
        return projectService.getProjectDtoById(id.toString());
    }

    @PutMapping(value = "/{id}")
    public ProjectDto updateProject(@PathVariable UUID id, @Validated @RequestBody CreateUpdateProjectDto createUpdateProjectDto) {
        return projectService.updateProject(id.toString(), createUpdateProjectDto);
    }

    @DeleteMapping(value = "/{id}")
    public MessageDto deleteProject(@PathVariable UUID id) {
        return projectService.deleteProject(id.toString());
    }
}
