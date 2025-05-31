package com.openclassrooms.chatop_backend.repository;

import com.openclassrooms.chatop_backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}