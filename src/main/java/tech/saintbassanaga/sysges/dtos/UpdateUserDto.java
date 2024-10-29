package tech.saintbassanaga.sysges.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tech.saintbassanaga.sysges.models.mapped.Role;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.sysges.models.User}
 */
public record UpdateUserDto(String email, Role role,
                            Set<UUID> projectUuids, Set<UUID> taskUuids, UUID uuid) implements Serializable {
}