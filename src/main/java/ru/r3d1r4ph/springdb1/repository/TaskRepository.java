package ru.r3d1r4ph.springdb1.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.r3d1r4ph.springdb1.entity.*;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, String>, JpaSpecificationExecutor<TaskEntity> {

    List<TaskEntity> findByProject(ProjectEntity projectEntity);

    List<TaskEntity> findByExecutor(UserEntity userEntity);

    List<TaskEntity> findByComments_TextContains(String text);

    List<TaskEntity> findAll(Specification<TaskEntity> specification);

    @Modifying
    @Query("update TaskEntity t set t.priority = ?1 where t.uuid = ?2")
    int setTaskPriorityById(Priority priority, String id);

    @Modifying
    @Query("update TaskEntity t set t.executor = ?1 where t.uuid = ?2")
    int setTaskExecutorById(UserEntity userEntity, String id);
}
