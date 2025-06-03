package com.openclassrooms.chatop_backend.dto;

import java.util.List;

public class RentalsListResponseDTO {
    public List<RentalResponseDTO> rentals;

    public RentalsListResponseDTO(List<RentalResponseDTO> rentals) {
        this.rentals = rentals;
    }
}