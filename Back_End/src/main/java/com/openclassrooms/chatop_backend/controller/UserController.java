package com.openclassrooms.chatop_backend.controller;

import com.openclassrooms.chatop_backend.dto.UserResponseDTO;
import com.openclassrooms.chatop_backend.model.User;
import com.openclassrooms.chatop_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer id) {
        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOpt.get();
        UserResponseDTO dto = new UserResponseDTO();
        dto.id = user.getId();
        dto.email = user.getEmail();
        dto.name = user.getName();
        dto.created_at = user.getCreatedAt();
        dto.updated_at = user.getUpdatedAt();
        return ResponseEntity.ok(dto);
    }
}