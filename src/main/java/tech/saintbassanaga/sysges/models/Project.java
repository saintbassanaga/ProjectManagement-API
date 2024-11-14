package tech.saintbassanaga.sysges.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;
import tech.saintbassanaga.sysges.models.mapped.ProjectState;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "project", indexes = {
        @Index(name = "Idx_project_states", columnList = "start_date, end_date, project_state"),
        @Index(name = "idx_project_version_unq", columnList = "version", unique = true)
})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Version
    @Column(name = "version")
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer version;

    private String titre;

    private String description;

    private Date startDate;

    private Date endDate;

    @Enumerated(EnumType.STRING)
    private ProjectState projectState;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},fetch = FetchType.LAZY)
    @JoinTable(name = "project_users",
            joinColumns = @JoinColumn(name = "project_uuid"),
            inverseJoinColumns = @JoinColumn(name = "users_uuid"))
    private Set<User> users = new LinkedHashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new LinkedHashSet<>();

}