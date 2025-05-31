package com.openclassrooms.chatop_backend.service;

import com.openclassrooms.chatop_backend.model.Rental;
import com.openclassrooms.chatop_backend.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}