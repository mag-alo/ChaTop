package com.openclassrooms.chatop_backend.repository;

import com.openclassrooms.chatop_backend.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
}