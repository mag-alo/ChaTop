package com.openclassrooms.chatop_backend.controller;

import com.openclassrooms.chatop_backend.dto.RentalResponseDTO;
import com.openclassrooms.chatop_backend.dto.RentalsListResponseDTO;
import com.openclassrooms.chatop_backend.dto.RentalUpdateDTO;
import com.openclassrooms.chatop_backend.model.Rental;
import com.openclassrooms.chatop_backend.model.User;
import com.openclassrooms.chatop_backend.security.JwtUtil;
import com.openclassrooms.chatop_backend.service.RentalService;
import com.openclassrooms.chatop_backend.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Value("${upload.dir:uploads}")
    private String uploadDir;

    public RentalController(RentalService rentalService, UserService userService, JwtUtil jwtUtil) {
        this.rentalService = rentalService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public RentalsListResponseDTO getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        List<RentalResponseDTO> dtos = rentals.stream().map(this::toDTO).collect(Collectors.toList());
        return new RentalsListResponseDTO(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRentalById(@PathVariable Integer id) {
        Optional<Rental> rentalOpt = rentalService.getRentalById(id);
        return rentalOpt.map(rental -> ResponseEntity.ok(toDTO(rental)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<RentalResponseDTO> createRental(
            @RequestParam String name,
            @RequestParam BigDecimal surface,
            @RequestParam BigDecimal price,
            @RequestParam String description,
            @RequestParam("picture") MultipartFile picture,
            @RequestHeader("Authorization") String authHeader
    ) throws IOException {
        // 1. Extraire l'email du token JWT
        String email = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            email = jwtUtil.extractEmail(jwt);
        }
        if (email == null) {
            return ResponseEntity.status(401).build();
        }

        // 2. Récupérer le user courant
        Optional<User> ownerOpt = userService.getUserByEmail(email);
        if (ownerOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // 3. Sauvegarder l'image
        String fileName = System.currentTimeMillis() + "_" + picture.getOriginalFilename();
        File dir = new File(System.getProperty("user.dir"), uploadDir);
        if (!dir.exists()) dir.mkdirs();
        File dest = new File(dir, fileName);
        picture.transferTo(dest);

        // 4. Créer et sauvegarder le rental
        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setPicture("/" + uploadDir + "/" + fileName);
        rental.setOwner(ownerOpt.get());
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());

        Rental created = rentalService.saveRental(rental);

        // 5. Mapper la réponse DTO
        return ResponseEntity.ok(toDTO(created));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<RentalResponseDTO> updateRental(
            @PathVariable Integer id,
            @RequestParam String name,
            @RequestParam BigDecimal surface,
            @RequestParam BigDecimal price,
            @RequestParam String description,
            @RequestHeader("Authorization") String authHeader
    ) {
        Optional<Rental> rentalOpt = rentalService.getRentalById(id);
        if (rentalOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Rental rental = rentalOpt.get();

        // Vérifie que l'utilisateur courant est bien le propriétaire
        String email = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            email = jwtUtil.extractEmail(jwt);
        }
        if (email == null || !rental.getOwner().getEmail().equals(email)) {
            return ResponseEntity.status(403).build();
        }

        // Met à jour les champs
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setUpdatedAt(LocalDateTime.now());

        Rental updated = rentalService.saveRental(rental);

        return ResponseEntity.ok(toDTO(updated));
    }

    // Méthode utilitaire pour mapper Rental -> RentalResponseDTO
    private RentalResponseDTO toDTO(Rental rental) {
        RentalResponseDTO dto = new RentalResponseDTO();
        dto.id = rental.getId();
        dto.name = rental.getName();
        dto.surface = rental.getSurface();
        dto.price = rental.getPrice();
        dto.picture = rental.getPicture();
        dto.description = rental.getDescription();
        dto.owner_id = rental.getOwner().getId();
        dto.created_at = rental.getCreatedAt();
        dto.updated_at = rental.getUpdatedAt();
        return dto;
    }
}