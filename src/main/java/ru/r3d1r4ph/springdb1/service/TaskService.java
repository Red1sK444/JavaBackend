package ru.r3d1r4ph.springdb1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.r3d1r4ph.springdb1.csv.TaskCsv;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateTaskDto;
import ru.r3d1r4ph.springdb1.dto.createupdate.UpdateExecutorDto;
import ru.r3d1r4ph.springdb1.dto.createupdate.UpdatePriorityDto;
import ru.r3d1r4ph.springdb1.dto.mapper.TaskMapper;
import ru.r3d1r4ph.springdb1.dto.response.MessageDto;
import ru.r3d1r4ph.springdb1.dto.response.TaskDto;
import ru.r3d1r4ph.springdb1.dto.search.FetchDto;
import ru.r3d1r4ph.springdb1.dto.search.SearchTasksByCommentTextDto;
import ru.r3d1r4ph.springdb1.entity.Priority;
import ru.r3d1r4ph.springdb1.entity.TaskEntity;
import ru.r3d1r4ph.springdb1.entity.UserEntity;
import ru.r3d1r4ph.springdb1.exception.IncorrectFieldNameForSearch;
import ru.r3d1r4ph.springdb1.exception.TaskNotFoundException;
import ru.r3d1r4ph.springdb1.repository.TaskRepository;
import ru.r3d1r4ph.springdb1.utils.Utils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final ProjectService projectService;

    private final UserService userService;

    private final TaskMapper taskMapper;

    @Transactional
    public TaskDto createTask(CreateUpdateTaskDto createUpdateTaskDto) {
        return taskMapper.toDto(taskRepository.save(taskMapper.toEntity(createUpdateTaskDto)));
    }

    @Transactional
    public TaskDto updateTask(String id, CreateUpdateTaskDto createUpdateTaskDto) {
        TaskEntity taskEntity = taskMapper.toEntity(createUpdateTaskDto);

        taskEntity.setUuid(id);
        taskEntity.setCreateDate(getTaskEntityById(id).getCreateDate());
        return taskMapper.toDto(taskRepository.save(taskEntity));
    }

    @Transactional
    public MessageDto deleteTask(String id) {
        taskRepository.deleteById(id);
        return new MessageDto("OK");
    }

    @Transactional(readOnly = true)
    public TaskDto getTaskDtoById(String uuid) {
        return taskMapper.toDto(getTaskEntityById(uuid));
    }

    @Transactional
    public MessageDto changePriority(String uuid, UpdatePriorityDto updatePriorityDto) {
        if (taskRepository.setTaskPriorityById(updatePriorityDto.getPriority(), uuid) != 0) {
            return new MessageDto("OK");
        } else {
            throw new RuntimeException("Заданию с id " + uuid + " не удалось сменить приоритет");
        }
    }

    @Transactional
    public MessageDto changeExecutor(String uuid, UpdateExecutorDto updateExecutorDto) {
        if (taskRepository.setTaskExecutorById(userService.getUserEntityById(updateExecutorDto.getExecutorId()), uuid) != 0) {
            return new MessageDto("OK");
        } else {
            throw new RuntimeException("Заданию с id " + uuid + " не удалось сменить исполнителя");
        }
    }

    @Transactional(readOnly = true)
    public Page<TaskDto> getTaskDtosByAllExceptTimeEstimateAndComments(Pageable pageable, FetchDto taskDto) {
        Specification<TaskEntity> specification = (root, query, cb) -> {
            var predicates = new ArrayList<>();
            taskDto.getFields().forEach((fieldName, fieldValue) -> {
                switch (fieldName) {
                    case "title":
                    case "description":
                        predicates.add(cb.equal(root.get(fieldName), fieldValue));
                        break;
                    case "createDate":
                    case "editDate":
                        predicates.add(cb.equal(root.get(fieldName), Utils.convertStringToDate(fieldValue)));
                        break;
                    case "priority":
                        predicates.add(cb.equal(root.get(fieldName), Priority.valueOf(fieldValue)));
                        break;
                    case "creator":
                    case "executor":
                        predicates.add(cb.equal(root.get(fieldName), userService.getUserEntityById(fieldValue)));
                        break;
                    case "project":
                        predicates.add(cb.equal(root.get(fieldName), projectService.getProjectEntityById(fieldValue)));
                        break;
                    default:
                        throw new IncorrectFieldNameForSearch("Неверное имя поля: " + fieldName);
                }
            });
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        var taskPage = taskRepository.findAll(specification, pageable);
        var taskDtos = taskPage.getContent().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(taskDtos, taskPage.getPageable(), taskPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getTasksDtoByCommentText(SearchTasksByCommentTextDto searchTasksByCommentTextDto) {
        return taskRepository.findByComments_TextContains(searchTasksByCommentTextDto.getCommentText()).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getTaskDtosByExecutor(String uuid) {
        return getTaskEntitiesByExecutor(userService.getUserEntityById(uuid)).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskEntity getTaskEntityById(String uuid) {
        return taskRepository.findById(uuid).orElseThrow(() -> new TaskNotFoundException("Задача с id " + uuid + " не найдена"));
    }

    @Transactional(readOnly = true)
    public List<TaskEntity> getTaskEntitiesByExecutor(UserEntity executor) {
        return taskRepository.findByExecutor(executor);
    }

    @Value("${csv.tasks:/csv/tasks.csv}")
    private String tasksPath;

    @Transactional
    public void loadTasksFromCsv() {
        Utils.parseToCsv(tasksPath, TaskCsv.class).stream().map(taskMapper::toEntity).forEach(taskRepository::save);
    }
}
