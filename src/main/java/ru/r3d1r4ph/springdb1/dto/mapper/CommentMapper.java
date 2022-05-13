package ru.r3d1r4ph.springdb1.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.r3d1r4ph.springdb1.csv.CommentCsv;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateCommentDto;
import ru.r3d1r4ph.springdb1.dto.response.CommentDto;
import ru.r3d1r4ph.springdb1.entity.CommentEntity;
import ru.r3d1r4ph.springdb1.entity.TaskEntity;
import ru.r3d1r4ph.springdb1.service.TaskService;
import ru.r3d1r4ph.springdb1.service.UserService;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserService userService;

    private final TaskService taskService;

    private final ModelMapper mapper;

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(CommentEntity.class, CommentDto.class)
                .addMappings(m -> {
                    m.skip(CommentDto::setId);
                    m.skip(CommentDto::setUser);
                    m.skip(CommentDto::setTaskIds);
                }).setPostConverter(toDtoConverter());
        mapper.createTypeMap(CreateUpdateCommentDto.class, CommentEntity.class)
                .addMappings(m -> {
                    m.skip(CommentEntity::setCreateDate);
                    m.skip(CommentEntity::setEditDate);
                    m.skip(CommentEntity::setUuid);
                    m.skip(CommentEntity::setUser);
                    m.skip(CommentEntity::setTasks);
                }).setPostConverter(toEntityConverter());
        mapper.createTypeMap(CommentCsv.class, CommentEntity.class)
                .addMappings(m -> {
                    m.skip(CommentEntity::setUuid);
                    m.skip(CommentEntity::setUser);
                    m.skip(CommentEntity::setTasks);
                }).setPostConverter(csvToEntityConverter());
    }

    private Converter<CreateUpdateCommentDto, CommentEntity> toEntityConverter() {
        return context -> {
            CreateUpdateCommentDto source = context.getSource();
            CommentEntity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<CommentCsv, CommentEntity> csvToEntityConverter() {
        return context -> {
            CommentCsv source = context.getSource();
            CommentEntity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<CommentEntity, CommentDto> toDtoConverter() {
        return context -> {
            CommentEntity source = context.getSource();
            CommentDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(CommentEntity source, CommentDto destination) {
        destination.setId(source.getUuid());
        destination.setUser(source.getUser().getUuid());
        destination.setTaskIds(
                source.getTasks().stream()
                        .map(TaskEntity::getUuid)
                        .collect(Collectors.toList())
        );
    }

    private void mapSpecificFields(CreateUpdateCommentDto source, CommentEntity destination) {
        var date = new Date();
        destination.setCreateDate(date);
        destination.setEditDate(date);
        destination.setUuid(UUID.randomUUID().toString());
        destination.setUser(userService.getUserEntityById(source.getUserId()));
        destination.setTasks(
                source.getTaskIds().stream()
                        .map(taskService::getTaskEntityById)
                        .collect(Collectors.toList())
        );
    }

    private void mapSpecificFields(CommentCsv source, CommentEntity destination) {
        destination.setUuid(source.getId());
        destination.setUser(userService.getUserEntityById(source.getUserId()));
        destination.setTasks(List.of(taskService.getTaskEntityById(source.getTaskId())));
    }

    public CommentEntity toEntity(CreateUpdateCommentDto createUpdateCommentDto) {
        return mapper.map(createUpdateCommentDto, CommentEntity.class);
    }

    public CommentEntity toEntity(CommentCsv commentCsv) {
        return mapper.map(commentCsv, CommentEntity.class);
    }

    public CommentDto toDto(CommentEntity commentEntity) {
        return mapper.map(commentEntity, CommentDto.class);
    }
}
