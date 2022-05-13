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
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {

    @Id
    @Column(name = "id")
    private String uuid;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "edit_date")
    private Date editDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToMany
    @JoinTable(name="comment_task",
            joinColumns = @JoinColumn(name="comment_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="task_id", referencedColumnName="id")
    )
    private List<TaskEntity> tasks;

    @Column
    private String text;
}
