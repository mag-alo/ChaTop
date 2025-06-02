package com.openclassrooms.chatop_backend.dto;

import java.time.LocalDateTime;

public class UserResponseDTO {
    public Integer id;
    public String email;
    public String name;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
}