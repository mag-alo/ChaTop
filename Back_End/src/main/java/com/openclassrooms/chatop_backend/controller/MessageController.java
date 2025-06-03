package com.openclassrooms.chatop_backend.controller;

import com.openclassrooms.chatop_backend.dto.MessageRequestDTO;
import com.openclassrooms.chatop_backend.model.Message;
import com.openclassrooms.chatop_backend.model.Rental;
import com.openclassrooms.chatop_backend.model.User;
import com.openclassrooms.chatop_backend.security.JwtUtil;
import com.openclassrooms.chatop_backend.service.MessageService;
import com.openclassrooms.chatop_backend.service.RentalService;
import com.openclassrooms.chatop_backend.service.UserService;

import io.swagger.v3.oas.annotations.Hidden;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final RentalService rentalService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public MessageController(
            MessageService messageService,
            RentalService rentalService,
            UserService userService,
            JwtUtil jwtUtil
    ) {
        this.messageService = messageService;
        this.rentalService = rentalService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Hidden
    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @Hidden
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id) {
        Optional<Message> messageOpt = messageService.getMessageById(id);
        return messageOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createMessage(
            @RequestBody MessageRequestDTO dto,
            @RequestHeader("Authorization") String authHeader
    ) {
        // Authentification utilisateur
        String email = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            email = jwtUtil.extractEmail(jwt);
        }
        if (email == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Optional<User> userOpt = userService.getUserByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Optional<Rental> rentalOpt = rentalService.getRentalById(dto.rental_id);
        if (rentalOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Rental not found");
        }

        // Création du message
        Message message = new Message();
        message.setMessage(dto.message);
        message.setRental(rentalOpt.get());
        message.setUser(userOpt.get());
        message.setCreatedAt(java.time.LocalDateTime.now());

        messageService.saveMessage(message);

        return ResponseEntity.ok(Map.of("message", "Message sent successfully"));
    }
}