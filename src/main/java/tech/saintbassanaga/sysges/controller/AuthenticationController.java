package tech.saintbassanaga.sysges.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.saintbassanaga.sysges.config.JwtUtils;
import tech.saintbassanaga.sysges.dtos.JwtResponse;
import tech.saintbassanaga.sysges.dtos.LoginRequest;
import tech.saintbassanaga.sysges.dtos.RegisterUserDto;
import tech.saintbassanaga.sysges.exception.GeneralException;
import tech.saintbassanaga.sysges.models.User;
import tech.saintbassanaga.sysges.models.mapped.Role;
import tech.saintbassanaga.sysges.repository.UserRepository;
import tech.saintbassanaga.sysges.exception.ApiResponse;

@RestController
@RequestMapping("/auth/")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthenticationController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterUserDto registerRequest) {
        try {
            // Check if username already exists
            if (userRepository.existByUsername(registerRequest.username())) {
                ApiResponse apiResponse = new ApiResponse("Error: Username is already taken!", 400, null);
                return ResponseEntity.badRequest().body(apiResponse);
            }

            // Check if email already exists
            if (userRepository.existsByEmail(registerRequest.email())) {
                ApiResponse apiResponse = new ApiResponse("Error: Email is already in use!", 400, null);
                return ResponseEntity.badRequest().body(apiResponse);
            }

            // Create new user object
            User user = new User();
            user.setUsername(registerRequest.username());
            user.setEmail(registerRequest.email());
            user.setPassword(passwordEncoder.encode(registerRequest.password())); // Encrypt password
            user.setRole(Role.MEMBER); // Default role

            // Save user to the database
            userRepository.save(user);

            ApiResponse apiResponse = new ApiResponse("User registered successfully!", 201, null);
            return ResponseEntity.status(201).body(apiResponse);
        } catch (GeneralException e) {
            ApiResponse apiResponse = new ApiResponse("Failed to register user: " + e.getMessage(), 500, null);
            return ResponseEntity.status(500).body(apiResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );

            // Set authentication in the context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String jwt = jwtUtils.generateToken(authentication);

            // Get user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Return JWT in the response
            JwtResponse response = new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
            ApiResponse apiResponse = new ApiResponse("User authenticated successfully", 200, response);
            return ResponseEntity.ok(apiResponse);
        } catch (GeneralException e) {
            ApiResponse apiResponse = new ApiResponse("Authentication failed: " + e.getMessage(), 401, null);
            return ResponseEntity.status(401).body(apiResponse);
        }
    }
}
