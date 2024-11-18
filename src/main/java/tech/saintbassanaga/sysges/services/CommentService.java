package tech.saintbassanaga.sysges.services;

import org.springframework.stereotype.Service;
import tech.saintbassanaga.sysges.models.Comment;
import tech.saintbassanaga.sysges.models.Project;
import tech.saintbassanaga.sysges.models.Task;
import tech.saintbassanaga.sysges.models.User;

import java.util.List;
import java.util.UUID;

/**
 *  Service interface for managing comments in the system.
 *  Created by saintbassanaga
 *  In the Project SysGes at Sun - 11/17/24
 */

@Service
public interface CommentService {

        /**
         * Retrieves all comments based on the provided UUID(s). Filters can be applied by Task UUID,
         * Project UUID, or User UUID, or a combination of these.
         *
         * @param taskUUID    the UUID of the task (optional, can be null)
         * @param projectUUID the UUID of the project (optional, can be null)
         * @param userUUID    the UUID of the user (optional, can be null)
         * @return a list of comments matching the specified criteria
         */
        List<Comment> findAllComments(UUID taskUUID, UUID projectUUID, UUID userUUID);

        /**
         * Updates an existing comment by its UUID.
         *
         * @param commentUUID the UUID of the comment to update
         * @param comment     the updated comment details
         * @return the updated comment
         */
        Comment updateComment(UUID commentUUID, Comment comment);

        /**
         * Deletes a comment by its UUID.
         *
         * @param commentUUID the UUID of the comment to delete
         */
        void deleteComment(UUID commentUUID);

        /**
         * Adds a new comment to the system.
         *
         * @param comment the comment to add
         * @return the added comment
         */
        Comment addComment(Comment comment);

}
