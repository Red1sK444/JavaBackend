package ru.r3d1r4ph.springdb1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "edit_date")
    private Date editDate;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private String email;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<TaskEntity> creatorTasks;

    @OneToMany(mappedBy = "executor", cascade = CascadeType.ALL)
    private List<TaskEntity> executorTasks;
}
