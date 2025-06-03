package com.openclassrooms.chatop_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RentalResponseDTO {
    public Integer id;
    public String name;
    public BigDecimal surface;
    public BigDecimal price;
    public String picture;
    public String description;
    public Integer owner_id;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
}