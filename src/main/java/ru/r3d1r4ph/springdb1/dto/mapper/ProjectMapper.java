package ru.r3d1r4ph.springdb1.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.r3d1r4ph.springdb1.csv.ProjectCsv;
import ru.r3d1r4ph.springdb1.csv.UserCsv;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateProjectDto;
import ru.r3d1r4ph.springdb1.dto.response.ProjectDto;
import ru.r3d1r4ph.springdb1.entity.ProjectEntity;
import ru.r3d1r4ph.springdb1.entity.UserEntity;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final ModelMapper mapper;

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(CreateUpdateProjectDto.class, ProjectEntity.class)
                .addMappings(m -> {
                    m.skip(ProjectEntity::setCreateDate);
                    m.skip(ProjectEntity::setEditDate);
                    m.skip(ProjectEntity::setUuid);
                }).setPostConverter(toEntityConverter());
        mapper.createTypeMap(ProjectEntity.class, ProjectDto.class)
                .addMappings(m -> m.skip(ProjectDto::setId)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(ProjectCsv.class, ProjectEntity.class)
                .addMappings(m -> m.skip(ProjectEntity::setUuid)).setPostConverter(csvToEntityConverter());
    }

    private Converter<CreateUpdateProjectDto, ProjectEntity> toEntityConverter() {
        return context -> {
            CreateUpdateProjectDto source = context.getSource();
            ProjectEntity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<ProjectEntity, ProjectDto> toDtoConverter() {
        return context -> {
            ProjectEntity source = context.getSource();
            ProjectDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<ProjectCsv, ProjectEntity> csvToEntityConverter() {
        return context -> {
            ProjectCsv source = context.getSource();
            ProjectEntity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(CreateUpdateProjectDto source, ProjectEntity destination) {
        var date = new Date();
        destination.setCreateDate(date);
        destination.setEditDate(date);
        destination.setUuid(UUID.randomUUID().toString());
    }

    private void mapSpecificFields(ProjectEntity source, ProjectDto destination) {
        destination.setId(source.getUuid());
    }

    private void mapSpecificFields(ProjectCsv source, ProjectEntity destination) {
        destination.setUuid(source.getId());
    }

    public ProjectEntity toEntity(CreateUpdateProjectDto createUpdateProjectDto) {
        return mapper.map(createUpdateProjectDto, ProjectEntity.class);
    }

    public ProjectEntity toEntity(ProjectCsv projectCsv) {
        return mapper.map(projectCsv, ProjectEntity.class);
    }

    public ProjectDto toDto(ProjectEntity projectEntity) {
        return mapper.map(projectEntity, ProjectDto.class);
    }
}
