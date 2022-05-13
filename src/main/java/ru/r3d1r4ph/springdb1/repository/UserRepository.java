package ru.r3d1r4ph.springdb1.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.r3d1r4ph.springdb1.entity.Role;
import ru.r3d1r4ph.springdb1.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {

    List<UserEntity> findAll(Specification<UserEntity> specification);

    UserEntity findByEmail(String email);

    @Modifying
    @Query("update UserEntity u set u.role = ?1 where u.uuid = ?2")
    int setUserRoleById(Role role, String id);
}
