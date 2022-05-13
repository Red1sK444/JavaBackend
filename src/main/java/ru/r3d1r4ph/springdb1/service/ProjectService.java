package ru.r3d1r4ph.springdb1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.r3d1r4ph.springdb1.csv.ProjectCsv;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateProjectDto;
import ru.r3d1r4ph.springdb1.dto.mapper.ProjectMapper;
import ru.r3d1r4ph.springdb1.dto.response.MessageDto;
import ru.r3d1r4ph.springdb1.dto.response.ProjectDto;
import ru.r3d1r4ph.springdb1.entity.ProjectEntity;
import ru.r3d1r4ph.springdb1.exception.ProjectNotFoundException;
import ru.r3d1r4ph.springdb1.repository.ProjectRepository;
import ru.r3d1r4ph.springdb1.utils.Utils;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectDto createProject(CreateUpdateProjectDto createUpdateProjectDto) {
        return projectMapper.toDto(projectRepository.save(projectMapper.toEntity(createUpdateProjectDto)));
    }

    @Transactional
    public ProjectDto updateProject(String id, CreateUpdateProjectDto createUpdateProjectDto) {
        ProjectEntity projectEntity = projectMapper.toEntity(createUpdateProjectDto);

        projectEntity.setUuid(id);
        projectEntity.setCreateDate(getProjectEntityById(id).getCreateDate());
        return projectMapper.toDto(projectRepository.save(projectEntity));
    }

    @Transactional
    public MessageDto deleteProject(String id) {
        projectRepository.deleteById(id);
        return new MessageDto("OK");
    }

    @Transactional(readOnly = true)
    public ProjectDto getProjectDtoById(String uuid) {
        return projectMapper.toDto(getProjectEntityById(uuid));
    }

    @Transactional(readOnly = true)
    public ProjectEntity getProjectEntityById(String uuid) {
        return projectRepository.findById(uuid)
                .orElseThrow(() -> new ProjectNotFoundException("Проект с id " + uuid + " не найден"));
    }

    @Value("${csv.projects:/csv/projects.csv}")
    private String projectsPath;

    @Transactional
    public void loadProjectsFromCsv() {
        Utils.parseToCsv(projectsPath, ProjectCsv.class)
                .stream()
                .map(projectMapper::toEntity)
                .forEach(projectRepository::save);
    }
}
