package tech.saintbassanaga.sysges.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.saintbassanaga.sysges.dtos.CommentDto;
import tech.saintbassanaga.sysges.exception.GeneralException;
import tech.saintbassanaga.sysges.models.Comment;
import tech.saintbassanaga.sysges.services.CommentService;
import tech.saintbassanaga.sysges.exception.ApiResponse;

import java.util.List;
import java.util.UUID;

/**
 * CommentController provides REST endpoints for managing comments.
 * It includes endpoints for creating, fetching, updating, and deleting comments.
 * <p>
 * Created by saintbassanaga
 * In the Project SysGes at Tue - 10/29/24
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Fetch all comments based on a specific UUID type (project, task, or user).
     *
     * @param projectUuid | userUuid | taskUuid  the UUID to filter comments by
     * @return a list of comments
     */
    @GetMapping
    public ResponseEntity<ApiResponse> findAllComments(
            @RequestParam(value = "projectUuid", required = false) UUID projectUuid,
            @RequestParam(value = "taskUuid", required = false) UUID taskUuid,
            @RequestParam(value = "userUuid", required = false) UUID userUuid) {
        try {
            List<Comment> comments = commentService.findAllComments(taskUuid, projectUuid, userUuid);
            ApiResponse apiResponse = new ApiResponse("Comments retrieved successfully", 200, comments);
            return ResponseEntity.ok(apiResponse);
        } catch (GeneralException e) {
            ApiResponse apiResponse = new ApiResponse("Failed to retrieve comments: " + e.getMessage(), 500, null);
            return ResponseEntity.status(500).body(apiResponse);
        }
    }

    /**
     * Add a new comment or an answer to an existing comment.
     *
     * @param comment the comment object
     * @return the created comment
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addComment(@RequestBody CommentDto comment) {
        try {
            Comment createdComment = commentService.addComment(comment);
            ApiResponse apiResponse = new ApiResponse("Comment created successfully", 201, createdComment);
            return ResponseEntity.status(201).body(apiResponse);
        } catch (GeneralException e) {
            ApiResponse apiResponse = new ApiResponse("Failed to add comment: " + e.getMessage(), 500, null);
            return ResponseEntity.status(500).body(apiResponse);
        }
    }

    /**
     * Admin only: Update an existing comment by UUID.
     *
     * @param commentUuid the UUID of the comment to update
     * @param comment     the updated comment object
     * @return the updated comment
     */
    @PutMapping("/admin/{commentUuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateComment(
            @PathVariable UUID commentUuid,
            @RequestBody Comment comment) {
        try {
            Comment updatedComment = commentService.updateComment(commentUuid, comment);
            ApiResponse apiResponse = new ApiResponse("Comment updated successfully", 200, updatedComment);
            return ResponseEntity.ok(apiResponse);
        } catch (GeneralException e) {
            ApiResponse apiResponse = new ApiResponse("Failed to update comment: " + e.getMessage(), 500, null);
            return ResponseEntity.status(500).body(apiResponse);
        }
    }

    /**
     * Admin only: Delete a comment by UUID. A comment can only be deleted if it is not an answer.
     *
     * @param commentUuid the UUID of the comment to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/admin/{commentUuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable UUID commentUuid) {
        try {
            commentService.deleteComment(commentUuid);
            ApiResponse apiResponse = new ApiResponse("Comment successfully deleted", 200, null);
            return ResponseEntity.ok(apiResponse);
        } catch (GeneralException e) {
            ApiResponse apiResponse = new ApiResponse("Failed to delete comment: " + e.getMessage(), 500, null);
            return ResponseEntity.status(500).body(apiResponse);
        }
    }
}
