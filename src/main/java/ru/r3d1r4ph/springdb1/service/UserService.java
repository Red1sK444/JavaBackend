package ru.r3d1r4ph.springdb1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.r3d1r4ph.springdb1.csv.UserCsv;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateUserDto;
import ru.r3d1r4ph.springdb1.dto.createupdate.UpdateRoleDto;
import ru.r3d1r4ph.springdb1.dto.mapper.UserMapper;
import ru.r3d1r4ph.springdb1.dto.response.MessageDto;
import ru.r3d1r4ph.springdb1.dto.response.UserDto;
import ru.r3d1r4ph.springdb1.dto.search.FetchDto;
import ru.r3d1r4ph.springdb1.entity.Role;
import ru.r3d1r4ph.springdb1.entity.UserEntity;
import ru.r3d1r4ph.springdb1.exception.IncorrectFieldNameForSearch;
import ru.r3d1r4ph.springdb1.exception.UserAlreadyExistsException;
import ru.r3d1r4ph.springdb1.exception.UserNotFoundException;
import ru.r3d1r4ph.springdb1.repository.UserRepository;
import ru.r3d1r4ph.springdb1.utils.Utils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional
    public UserDto createUser(CreateUpdateUserDto createUpdateUserDto) {
        if (userRepository.findByEmail(createUpdateUserDto.getEmail()) != null) {
            throw new UserAlreadyExistsException("Пользователь с email: " + createUpdateUserDto.getEmail() + " уже существует");
        }

        return userMapper.toDto(userRepository.save(userMapper.toEntity(createUpdateUserDto)));
    }

    @Transactional
    public UserDto updateUser(String id, CreateUpdateUserDto createUpdateUserDto) {
        UserEntity userEntity = userMapper.toEntity(createUpdateUserDto);

        userEntity.setUuid(id);
        userEntity.setCreateDate(getUserEntityById(id).getCreateDate());
        return userMapper.toDto(userRepository.save(userEntity));
    }

    @Transactional
    public MessageDto deleteUser(String id) {
        userRepository.deleteById(id);
        return new MessageDto("OK");
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoById(String uuid) {
        return userMapper.toDto(getUserEntityById(uuid));
    }

    @Transactional(readOnly = true)
    public UserEntity getUserEntityById(String uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + uuid + " не найден"));
    }

    @Transactional
    public MessageDto changeRole(String uuid, UpdateRoleDto updateRoleDto) {
        if (userRepository.setUserRoleById(updateRoleDto.getRole(), uuid) != 0) {
            return new MessageDto("OK");
        } else {
            throw new RuntimeException("Пользователю с id " + uuid + " не удалось сменить роль");
        }
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUserDtosByAllExceptPassword(FetchDto userDto) {
        return userRepository
                .findAll((root, query, cb) -> {
                    var predicates = new ArrayList<>();
                    userDto.getFields().forEach((fieldName, fieldValue) -> {
                        switch (fieldName) {
                            case "fullName":
                            case "email":
                                predicates.add(cb.equal(root.get(fieldName), fieldValue));
                                break;
                            case "createDate":
                            case "editDate":
                                predicates.add(cb.equal(root.get(fieldName), Utils.convertStringToDate(fieldValue)));
                                break;
                            case "role":
                                predicates.add(cb.equal(root.get(fieldName), Role.valueOf(fieldValue)));
                                break;
                            default:
                                throw new IncorrectFieldNameForSearch("Неверное имя поля: " + fieldName);
                        }
                    });
                    return cb.and(predicates.toArray(new Predicate[0]));
                })
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Value("${csv.users:/csv/users.csv}")
    private String usersPath;

    @Transactional
    public void loadUsersFromCsv() {
        Utils.parseToCsv(usersPath, UserCsv.class)
                .stream()
                .map(userMapper::toEntity)
                .forEach(userRepository::save);
    }
}
