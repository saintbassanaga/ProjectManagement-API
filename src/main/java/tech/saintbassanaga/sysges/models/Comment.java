package tech.saintbassanaga.sysges.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.LastModifiedDate;
import tech.saintbassanaga.sysges.models.mapped.AuditingEntity;
import tech.saintbassanaga.sysges.models.mapped.CommentType;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment",
        uniqueConstraints = @UniqueConstraint(columnNames = {"task_uuid", "project_uuid"})
)
public class Comment extends AuditingEntity {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_uuid")
    private Task task;

    private CommentType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_uuid")
    private Comment parent;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_uuid")
    private Project project;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    private String content;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}