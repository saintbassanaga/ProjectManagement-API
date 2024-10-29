package tech.saintbassanaga.sysges.controller;

/**
 * Created by saintbassanaga {stpaul}
 * In the Project SysGes at Tue - 10/29/24
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.saintbassanaga.sysges.dtos.*;
import tech.saintbassanaga.sysges.models.mapped.ProjectState;
import tech.saintbassanaga.sysges.services.serviceImpls.UserServiceImpls;

import java.util.List;
import java.util.UUID;

/**
 * UserController is responsible for handling user-related HTTP requests
 * and delegating to UserServiceImpls for business logic.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpls userService;

    public UserController(UserServiceImpls userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to create a new user.
     *
     * @param userCreationDto the data transfer object with user creation information
     * @return a response entity with a confirmation message
     */
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserCreationDto userCreationDto) {
        String response = userService.createUser(userCreationDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to update an existing user.
     *
     * @param userUuid      the UUID of the user to be updated
     * @param updateUserDto the DTO containing updated information for the user
     * @return a response entity with the updated user information
     */
    @PutMapping("/{userUuid}/update")
    public ResponseEntity<ShowUserDto> updateUser(@PathVariable UUID userUuid, @RequestBody UpdateUserDto updateUserDto) {
        ShowUserDto updatedUser = userService.update(userUuid, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Endpoint to retrieve all users in short format.
     *
     * @return a response entity with the list of all users
     */
    @GetMapping
    public ResponseEntity<List<ShortUserDto>> findAllUsers() {
        List<ShortUserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Endpoint to find a user by UUID.
     *
     * @param uuid the UUID of the user to retrieve
     * @return a response entity with the found user details
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<ShowUserDto> findUserByUuid(@PathVariable UUID uuid) {
        ShowUserDto user = userService.findByUuid(uuid);
        return ResponseEntity.ok(user);
    }

    /**
     * Endpoint to retrieve users with tasks that have specific criteria.
     *
     * @param status   the task status filter
     * @param role     the user role filter
     * @param location the user location filter
     * @return a response entity with the list of matching users
     */
    @GetMapping("/tasks")
    public ResponseEntity<List<ShortUserDto>> findUsersWithTaskRunning(
            @RequestParam String state,
            @RequestParam String role,
            @RequestParam String location) {
        List<ShortUserDto> users = userService.findUserWithTaskRunning(state, role, location);
        return ResponseEntity.ok(users);
    }

    /**
     * Endpoint to retrieve users associated with a specific project state.
     *
     * @param projectState the project state to filter users by
     * @return a response entity with the list of matching users
     */
    @GetMapping("/projects")
    public ResponseEntity<List<ShortUserDto>> findUsersWithProjectState(@RequestParam ProjectState projectState) {
        List<ShortUserDto> users = userService.findUserWithProjectState(projectState);
        return ResponseEntity.ok(users);
    }
}
