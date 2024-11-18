package tech.saintbassanaga.sysges.dtos;

import tech.saintbassanaga.sysges.models.mapped.CommentType;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.sysges.models.Comment}
 */
public record CommentDto(UUID taskUuid, UUID taskProjectUuid, CommentType type, UUID parentUuid, UUID userUuid, String content) implements Serializable {
  }