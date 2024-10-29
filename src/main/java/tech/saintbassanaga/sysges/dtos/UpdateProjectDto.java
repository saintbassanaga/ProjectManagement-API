package tech.saintbassanaga.sysges.dtos;

import jakarta.validation.constraints.NotNull;
import tech.saintbassanaga.sysges.models.mapped.ProjectState;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.sysges.models.Project}
 */
public record UpdateProjectDto(String title, @NotNull(message = "Could Not be Null") String description  ,Date endDate,
                               ProjectState projectState, Set<UUID> users, Date startDate) implements Serializable {
}