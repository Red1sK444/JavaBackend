package ru.r3d1r4ph.springdb1.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "edit_date")
    private Date editDate;

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private UserEntity executor;

    @Enumerated(EnumType.STRING)
    @Column
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectEntity project;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_estimate")
    private Date timeEstimate;

    @ManyToMany(mappedBy = "tasks")
    private List<CommentEntity> comments;
}
