package ru.r3d1r4ph.springdb1.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.r3d1r4ph.springdb1.csv.ProjectCsv;
import ru.r3d1r4ph.springdb1.csv.TaskCsv;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateTaskDto;
import ru.r3d1r4ph.springdb1.dto.response.TaskDto;
import ru.r3d1r4ph.springdb1.entity.ProjectEntity;
import ru.r3d1r4ph.springdb1.entity.TaskEntity;
import ru.r3d1r4ph.springdb1.service.ProjectService;
import ru.r3d1r4ph.springdb1.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserService userService;

    private final ProjectService projectService;

    private final ModelMapper mapper;

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(TaskEntity.class, TaskDto.class)
                .addMappings(m -> {
                    m.skip(TaskDto::setId);
                    m.skip(TaskDto::setCreator);
                    m.skip(TaskDto::setExecutor);
                    m.skip(TaskDto::setProject);
                }).setPostConverter(toDtoConverter());
        mapper.createTypeMap(CreateUpdateTaskDto.class, TaskEntity.class)
                .addMappings(m -> {
                    m.skip(TaskEntity::setCreateDate);
                    m.skip(TaskEntity::setEditDate);
                    m.skip(TaskEntity::setUuid);
                    m.skip(TaskEntity::setCreator);
                    m.skip(TaskEntity::setExecutor);
                    m.skip(TaskEntity::setProject);
                }).setPostConverter(toEntityConverter());
        mapper.createTypeMap(TaskCsv.class, TaskEntity.class)
                .addMappings(m -> {
                    m.skip(TaskEntity::setUuid);
                    m.skip(TaskEntity::setCreator);
                    m.skip(TaskEntity::setExecutor);
                    m.skip(TaskEntity::setProject);
                }).setPostConverter(csvToEntityConverter());
    }

    private Converter<CreateUpdateTaskDto, TaskEntity> toEntityConverter() {
        return context -> {
            CreateUpdateTaskDto source = context.getSource();
            TaskEntity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<TaskEntity, TaskDto> toDtoConverter() {
        return context -> {
            TaskEntity source = context.getSource();
            TaskDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<TaskCsv, TaskEntity> csvToEntityConverter() {
        return context -> {
            TaskCsv source = context.getSource();
            TaskEntity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(TaskEntity source, TaskDto destination) {
        destination.setId(source.getUuid());
        destination.setCreator(source.getCreator().getUuid());
        destination.setExecutor(source.getExecutor().getUuid());
        destination.setProject(source.getProject().getUuid());
    }

    private void mapSpecificFields(CreateUpdateTaskDto source, TaskEntity destination) {
        var date = new Date();
        destination.setCreateDate(date);
        destination.setEditDate(date);
        destination.setUuid(UUID.randomUUID().toString());
        destination.setCreator(userService.getUserEntityById(source.getCreatorId()));
        destination.setExecutor(userService.getUserEntityById(source.getExecutorId()));
        destination.setProject(projectService.getProjectEntityById(source.getProjectId()));
    }

    private void mapSpecificFields(TaskCsv source, TaskEntity destination) {
        destination.setUuid(source.getId());
        destination.setCreator(userService.getUserEntityById(source.getCreatorId()));
        destination.setExecutor(userService.getUserEntityById(source.getExecutorId()));
        destination.setProject(projectService.getProjectEntityById(source.getProjectId()));
    }

    public TaskEntity toEntity(CreateUpdateTaskDto createUpdateTaskDto) {
        return mapper.map(createUpdateTaskDto, TaskEntity.class);
    }

    public TaskEntity toEntity(TaskCsv taskCsv) {
        return mapper.map(taskCsv, TaskEntity.class);
    }

    public TaskDto toDto(TaskEntity taskEntity) {
        return mapper.map(taskEntity, TaskDto.class);
    }
}
