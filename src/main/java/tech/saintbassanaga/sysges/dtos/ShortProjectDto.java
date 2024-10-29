package tech.saintbassanaga.sysges.dtos;

import tech.saintbassanaga.sysges.models.mapped.ProjectState;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link tech.saintbassanaga.sysges.models.Project}
 */
public record ShortProjectDto(String titre, String description, Date startDate, Date endDate,
                              ProjectState projectState) implements Serializable {
}