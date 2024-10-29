package tech.saintbassanaga.sysges.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tech.saintbassanaga.sysges.models.mapped.Role;
import tech.saintbassanaga.sysges.models.mapped.TaskState;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.sysges.models.User}
 */
public record ShowUserDto(String name, String surname, @NotNull @Size(min = 6, max = 15) String username, String email,
                          Role role, Set<TaskDto> tasks) implements Serializable {
}