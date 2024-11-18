package tech.saintbassanaga.sysges.services.serviceImpls;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.saintbassanaga.sysges.dtos.CommentDto;
import tech.saintbassanaga.sysges.dtos.DtoMapper;
import tech.saintbassanaga.sysges.models.Comment;
import tech.saintbassanaga.sysges.models.mapped.CommentType;
import tech.saintbassanaga.sysges.repository.CommentRepository;
import tech.saintbassanaga.sysges.services.CommentService;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation for managing comments in the system.
 */
@Service
public class CommentServiceImpls implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpls(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Retrieves all comments based on the provided UUID(s). Filters can be applied by Task UUID,
     * Project UUID, or User UUID, or a combination of these.
     *
     * @param taskUUID    the UUID of the task (optional, can be null)
     * @param projectUUID the UUID of the project (optional, can be null)
     * @param userUUID    the UUID of the user (optional, can be null)
     * @return a list of comments matching the specified criteria
     */
    @Override
    public List<Comment> findAllComments(UUID taskUUID, UUID projectUUID, UUID userUUID) {
        if (taskUUID == null && projectUUID == null && userUUID == null) {
            throw new IllegalArgumentException("At least one UUID (Task, Project, or User) must be provided.");
        }

        return commentRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (taskUUID != null) {
                predicates.add(criteriaBuilder.equal(root.get("task").get("uuid"), taskUUID));
            }
            if (projectUUID != null) {
                predicates.add(criteriaBuilder.equal(root.get("project").get("uuid"), projectUUID));
            }
            if (userUUID != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("uuid"), userUUID));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    /**
     * Updates an existing comment by its UUID.
     *
     * @param commentUUID the UUID of the comment to update
     * @param comment     the updated comment details
     * @return the updated comment
     */
    @Transactional
    @Override
    public Comment updateComment(UUID commentUUID, Comment comment) {
        Comment existingComment = commentRepository.findById(commentUUID)
                .orElseThrow(() -> new RuntimeException("Comment not found with UUID: " + commentUUID));

        existingComment.setContent(comment.getContent());
        return commentRepository.save(existingComment);
    }

    /**
     * Deletes a comment by its UUID. A comment of type ANSWER cannot be deleted.
     *
     * @param commentUUID the UUID of the comment to delete
     * @throws RuntimeException if the comment is not found or if the comment type is ANSWER
     */
    @Transactional
    @Override
    public void deleteComment(UUID commentUUID) {
        Comment existingComment = commentRepository.findById(commentUUID)
                .orElseThrow(() -> new RuntimeException("Comment not found with UUID: " + commentUUID));

        if (existingComment.getType() == CommentType.HEAD) {
            throw new RuntimeException("Cannot delete a comment of type HEAD.");
        }

        commentRepository.deleteById(commentUUID);
    }


    /**
     * Adds a comment to the system. If the comment is an answer, it sets the parentCommentUUID.
     *
     * @param commentDto the comment to add
     * @return the saved comment
     * @throws RuntimeException if the parent comment does not exist when adding an answer
     */
    @Transactional

    public Comment addComment(CommentDto commentDto) {

        Comment comment = DtoMapper.commentDto(commentDto);
        // If the comment is an ANSWER, validate and set the parent comment
        if (commentDto.type() == CommentType.ANSWER) {
            if (commentDto.parentUuid() == null) {
                throw new RuntimeException("Parent comment UUID is required when adding an ANSWER.");
            }
            // Verify that the parent comment exists
            Comment parentComment = commentRepository.findById(commentDto.parentUuid())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found with UUID: " + commentDto.parentUuid()));

            // Set the parentCommentUUID for the answer comment
            comment.setParent(parentComment.getParent());
        }

        // Save the comment to the repository
        return commentRepository.save(comment);
    }

}
