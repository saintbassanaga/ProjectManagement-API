package tech.saintbassanaga.sysges.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import tech.saintbassanaga.sysges.models.mapped.AuditingEntity;
import tech.saintbassanaga.sysges.models.mapped.Role;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user", indexes = {
        @Index(name = "idx_user_email",columnList = "email"),
        @Index(name="idx_username_creation_date", columnList = "creation_date")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User extends AuditingEntity {

    private String name;
    @NonNull
    private String surname;

    @NotNull
    @Size(min = 6, max = 15)
    @Column(nullable = false, unique = true, name = "username")
    private String username;

    @NonNull
    @Column(nullable = false, length = 25)
    private String email;

    @NotNull
    @Size(min = 8, max = 100)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String location;

    @Transient
    private LocalDateTime lastModifiedDate;

    @BatchSize(size = 10)
    @ManyToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},fetch = FetchType.EAGER)
    private Set<Project> projects = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Task> tasks = new LinkedHashSet<>();

}