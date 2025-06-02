package com.openclassrooms.chatop_backend.controller;

import com.openclassrooms.chatop_backend.dto.*;
import com.openclassrooms.chatop_backend.model.User;
import com.openclassrooms.chatop_backend.security.JwtUtil;
import com.openclassrooms.chatop_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        Optional<User> userOpt = userService.getUserByEmail(loginRequest.email);
        System.out.println(loginRequest.email + " " + loginRequest.password);        
        if (userOpt.isPresent() && passwordEncoder.matches(loginRequest.password, userOpt.get().getPassword())) {
            System.out.println("User authenticated successfully: " + userOpt.get().getEmail());
            String token = jwtUtil.generateToken(loginRequest.email);
            AuthResponseDTO response = new AuthResponseDTO();
            response.token = token;
            response.user = toUserResponseDTO(userOpt.get());
            return ResponseEntity.ok(response);
        }
        System.out.println("User authenticated failed " + loginRequest.password);
        return ResponseEntity.status(401).body("Invalid credentials !");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequest) {
        if (userService.getUserByEmail(registerRequest.email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        User user = new User();
        user.setEmail(registerRequest.email);
        user.setName(registerRequest.name);
        user.setPassword(passwordEncoder.encode(registerRequest.password));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User created = userService.saveUser(user);

        UserResponseDTO userDto = toUserResponseDTO(created);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtUtil.extractEmail(token);
            Optional<User> user = userService.getUserByEmail(email);
            if (user.isPresent()) {
                return ResponseEntity.ok(toUserResponseDTO(user.get()));
            }
        }
        return ResponseEntity.status(401).body("Unauthorized");
    }

    private UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.id = user.getId();
        dto.email = user.getEmail();
        dto.name = user.getName();
        dto.created_at = user.getCreatedAt();
        dto.updated_at = user.getUpdatedAt();
        return dto;
    }
}