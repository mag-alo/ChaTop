package com.openclassrooms.chatop_backend.service;

import com.openclassrooms.chatop_backend.dto.RentalResponseDTO;
import com.openclassrooms.chatop_backend.model.Rental;
import com.openclassrooms.chatop_backend.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Integer id) {
        return rentalRepository.findById(id);
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public void deleteRental(Integer id) {
        rentalRepository.deleteById(id);
    }

    public RentalRepository getRentalRepository() {
        return rentalRepository;
    }

    public RentalResponseDTO toDTO(Rental rental) {
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

    public List<RentalResponseDTO> toDTOList(List<Rental> rentals) {
        return rentals.stream().map(this::toDTO).collect(Collectors.toList());
    }
}