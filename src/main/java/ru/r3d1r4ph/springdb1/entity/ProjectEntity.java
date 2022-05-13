package ru.r3d1r4ph.springdb1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {

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
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<TaskEntity> tasks;
}
