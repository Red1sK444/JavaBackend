package ru.r3d1r4ph.springdb1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.r3d1r4ph.springdb1.entity.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {
}
