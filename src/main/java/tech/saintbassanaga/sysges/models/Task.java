package tech.saintbassanaga.sysges.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tech.saintbassanaga.sysges.models.mapped.AuditingEntity;
import tech.saintbassanaga.sysges.models.mapped.TaskSeverity;
import tech.saintbassanaga.sysges.models.mapped.TaskState;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "task", indexes = {
        @Index(name = "Idx_task_user_uuid", columnList = "user_uuid, due_date, creation_date",unique = true),
        @Index(name = "idx_task_project_uuid_unq", columnList = "project_uuid", unique = true)
})
public class Task extends AuditingEntity {
    @ManyToOne
    @JoinColumn(name = "project_uuid")
    private Project project;

    private String title;

    private String description;
    private Date dueDate;
    @Enumerated(EnumType.STRING)
    private TaskState state;
    @Enumerated(EnumType.STRING)
    private TaskSeverity severity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

}