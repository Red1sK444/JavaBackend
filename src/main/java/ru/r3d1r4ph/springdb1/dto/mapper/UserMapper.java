package ru.r3d1r4ph.springdb1.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.r3d1r4ph.springdb1.csv.UserCsv;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateUserDto;
import ru.r3d1r4ph.springdb1.dto.response.UserDto;
import ru.r3d1r4ph.springdb1.entity.UserEntity;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper mapper;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(CreateUpdateUserDto.class, UserEntity.class)
                .addMappings(m -> {
                    m.skip(UserEntity::setCreateDate);
                    m.skip(UserEntity::setEditDate);
                    m.skip(UserEntity::setUuid);
                    m.skip(UserEntity::setPassword);
                }).setPostConverter(toEntityConverter());
        mapper.createTypeMap(UserEntity.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setId)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(UserCsv.class, UserEntity.class)
                .addMappings(m -> {
                    m.skip(UserEntity::setUuid);
                    m.skip(UserEntity::setPassword);
                }).setPostConverter(csvToEntityConverter());
    }

    private Converter<CreateUpdateUserDto, UserEntity> toEntityConverter() {
        return context -> {
            CreateUpdateUserDto source = context.getSource();
            UserEntity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<UserCsv, UserEntity> csvToEntityConverter() {
        return context -> {
            UserCsv source = context.getSource();
            UserEntity destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<UserEntity, UserDto> toDtoConverter() {
        return context -> {
            UserEntity source = context.getSource();
            UserDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(CreateUpdateUserDto source, UserEntity destination) {
        var date = new Date();
        destination.setCreateDate(date);
        destination.setEditDate(date);
        destination.setUuid(UUID.randomUUID().toString());
        destination.setPassword(passwordEncoder.encode(source.getPassword()));
    }

    private void mapSpecificFields(UserEntity source, UserDto destination) {
        destination.setId(source.getUuid());
    }

    private void mapSpecificFields(UserCsv source, UserEntity destination) {
        destination.setUuid(source.getId());
        destination.setPassword(passwordEncoder.encode(source.getPassword()));
    }

    public UserEntity toEntity(CreateUpdateUserDto createUpdateUserDto) {
        return mapper.map(createUpdateUserDto, UserEntity.class);
    }

    public UserEntity toEntity(UserCsv userCsv) {
        return mapper.map(userCsv, UserEntity.class);
    }

    public UserDto toDto(UserEntity userEntity) {
        return mapper.map(userEntity, UserDto.class);
    }
}
