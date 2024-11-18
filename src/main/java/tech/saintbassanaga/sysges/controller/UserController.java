package tech.saintbassanaga.sysges.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.saintbassanaga.sysges.dtos.*;
import tech.saintbassanaga.sysges.exception.ApiResponse;
import tech.saintbassanaga.sysges.models.mapped.ProjectState;
import tech.saintbassanaga.sysges.services.serviceImpls.UserServiceImpls;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpls userService;

    public UserController(UserServiceImpls userService) {
        this.userService = userService;
    }

    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserCreationDto userCreationDto) {
        String response = userService.createUser(userCreationDto);
        ApiResponse apiResponse = new ApiResponse(response, 201, response);
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PutMapping("/admin/{userUuid}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable UUID userUuid,
            @RequestBody UpdateUserDto updateUserDto) {
        ShowUserDto updatedUser = userService.update(userUuid, updateUserDto);
        ApiResponse apiResponse = new ApiResponse("User updated successfully", 200, updatedUser);
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/admin/{userUuid}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> changeUserRole(
            @PathVariable UUID userUuid,
            @RequestParam String role) {
        if (!role.equals("ADMIN") && !role.equals("USER")) {
            ApiResponse apiResponse = new ApiResponse("Invalid role. Role must be 'ADMIN' or 'USER'.", 400);
            return ResponseEntity.badRequest().body(apiResponse);
        }

        String response = userService.changeUserRole(userUuid, role);
        ApiResponse apiResponse = new ApiResponse(response + role, 200, response);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAllUsers() {
        List<ShortUserDto> users = userService.findAll();
        ApiResponse apiResponse = new ApiResponse("Users retrieved successfully", 200, users);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse> findUserByUuid(@PathVariable UUID uuid) {
        ShowUserDto user = userService.findByUuid(uuid);
        ApiResponse apiResponse = new ApiResponse("User found successfully", 200, user);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse> findUsersWithTaskRunning(
            @RequestParam String state,
            @RequestParam String role,
            @RequestParam String location) {
        List<ShortUserDto> users = userService.findUserWithTaskRunning(state, role, location);
        ApiResponse apiResponse = new ApiResponse("Users with tasks found", 200, users);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/projects")
    public ResponseEntity<ApiResponse> findUsersWithProjectState(@RequestParam ProjectState projectState) {
        List<ShortUserDto> users = userService.findUserWithProjectState(projectState);
        ApiResponse apiResponse = new ApiResponse("Users with project state found", 200, users);
        return ResponseEntity.ok(apiResponse);
    }
}
