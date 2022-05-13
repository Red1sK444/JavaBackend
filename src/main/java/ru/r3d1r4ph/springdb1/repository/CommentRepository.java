package ru.r3d1r4ph.springdb1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.r3d1r4ph.springdb1.entity.CommentEntity;
import ru.r3d1r4ph.springdb1.entity.TaskEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, String> {
}
