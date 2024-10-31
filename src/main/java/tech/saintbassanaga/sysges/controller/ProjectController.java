package tech.saintbassanaga.sysges.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.saintbassanaga.sysges.dtos.*;
import tech.saintbassanaga.sysges.exception.ApiResponse;
import tech.saintbassanaga.sysges.models.Project;
import tech.saintbassanaga.sysges.services.ProjectService;
import java.util.List;
import java.util.UUID;

/**
 * REST Controller for managing Project operations.
 * Provides endpoints for creating, updating, retrieving, and deleting projects
 * with validation, structured responses, and exception handling.
 * <p>
 * Created by saintbassanaga  - Tue - 10/29/24
 */

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Creates a new project.
     *
     * @param createProjectDto the DTO containing project creation data
     * @return a ResponseEntity containing a success message with the new project UUID
     */
    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody CreateProjectDto createProjectDto) {
        try {
            String response = projectService.createProject(createProjectDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(response, HttpStatus.CREATED.value()));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating project", ex);
        }
    }

    /**
     * Updates an existing project.
     *
     * @param userUuid the UUID of the user performing the update
     * @param updateProjectDto the DTO containing updated project information
     * @return a ResponseEntity with the updated Project
     */
    @PatchMapping("/{userUuid}")
    public ResponseEntity<?> updateProject(@PathVariable UUID userUuid,
                                           @Valid @RequestBody UpdateProjectDto updateProjectDto) {
        try {
            Project updatedProject = projectService.update(userUuid, updateProjectDto);
            return ResponseEntity.ok(new ApiResponse("Project updated successfully", HttpStatus.OK.value(), updatedProject));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project or User not found", ex);
        }
    }

    /**
     * Retrieves all projects in a summarized format.
     *
     * @return a list of ShortProjectDto with summarized project information
     */
    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        try {
            List<ShortProjectDto> projects = projectService.findAll();
            return ResponseEntity.ok(new ApiResponse("Projects retrieved successfully", HttpStatus.OK.value(), projects));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving projects", ex);
        }
    }

    /**
     * Retrieves a specific project by UUID.
     *
     * @param uuid the UUID of the project to retrieve
     * @return a ShowProjectDto with detailed project information
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getProjectByUuid(@PathVariable UUID uuid) {
        try {
            ShowProjectDto project = projectService.findByUuid(uuid);
            return ResponseEntity.ok(new ApiResponse("Project retrieved successfully", HttpStatus.OK.value(), project));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found", ex);
        }
    }

    /**
     * Deletes a project by UUID.
     *
     * @param uuid the UUID of the project to delete
     * @return a no-content response upon successful deletion
     */
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteProject(@PathVariable UUID uuid) {
        try {
            projectService.deleteUser(uuid);
            return ResponseEntity.ok(new ApiResponse("Project deleted successfully", HttpStatus.OK.value()));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found", ex);
        }
    }

}

