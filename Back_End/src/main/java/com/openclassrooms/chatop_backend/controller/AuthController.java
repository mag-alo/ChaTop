package com.openclassrooms.chatop_backend.controller;

import com.openclassrooms.chatop_backend.model.User;
import com.openclassrooms.chatop_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> userOpt = userService.getUserByEmail(loginRequest.getEmail());
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(loginRequest.getPassword())) {
            // Ici tu peux générer un JWT ou retourner l'utilisateur selon le besoin du Front_End
            return ResponseEntity.ok(userOpt.get());
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        User created = userService.saveUser(user);
        return ResponseEntity.ok(created);
    }

    // @GetMapping("/me")
    // public ResponseEntity<?> getCurrentUser(@RequestParam String email) {
    //     // En production, récupérer l'email depuis le token JWT ou la session
    //     // L'email (ou username) est dans principal.getName() si Spring Security est bien configuré
    //     Optional<User> user = userService.getUserByEmail(email);
    //     return user.map(ResponseEntity::ok)
    //                .orElseGet(() -> ResponseEntity.status(404).body("User not found"));
    // }    
}