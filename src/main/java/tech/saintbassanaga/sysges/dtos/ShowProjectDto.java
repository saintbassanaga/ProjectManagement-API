package tech.saintbassanaga.sysges.dtos;

import tech.saintbassanaga.sysges.models.mapped.ProjectState;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.sysges.models.Project}
 */
public record ShowProjectDto(String titre, String description, Date startDate, Date endDate, ProjectState projectState,
                         Set<UUID> userUuids) implements Serializable {
}