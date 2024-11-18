package tech.saintbassanaga.sysges.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.saintbassanaga.sysges.models.mapped.AuditingEntity;
import tech.saintbassanaga.sysges.models.mapped.Role;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_username_creation_date", columnList = "creation_date")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User extends AuditingEntity implements UserDetails {

    @Column(nullable = false)
    private String name;

    @NotNull(message = "Surname must not be null")
    @Column(nullable = false)
    private String surname;

    @NotNull(message = "username must not be null")
    @Size(min = 6, max = 15)
    @Column(nullable = false, unique = true, name = "username")
    private String username;

    @NotNull
    @Email
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @NotNull
    @Size(min = 8, max = 100)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(length = 100)
    private String location;

    @BatchSize(size = 10)
    @ManyToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    private Set<Project> projects = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Task> tasks = new LinkedHashSet<>();

    /**
     * Returns the authorities granted to the user.
     * Maps the user's role to a `GrantedAuthority`.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * Indicates whether the user's account is expired.
     *
     * @return {@code true} if the account is non-expired; {@code false} otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Default to true; add logic if needed
    }

    /**
     * Indicates whether the user's account is locked.
     *
     * @return {@code true} if the account is non-locked; {@code false} otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Default to true; add logic if needed
    }

    /**
     * Indicates whether the user's credentials are expired.
     *
     * @return {@code true} if credentials are non-expired; {@code false} otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Default to true; add logic if needed
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return {@code true} if the user is enabled; {@code false} otherwise
     */
    @Override
    public boolean isEnabled() {
        return true; // Default to true; add logic if needed
    }
}
