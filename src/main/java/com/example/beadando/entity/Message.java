package com.example.beadando.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_name") // Mivel az adatbázisban snake_case van
    private String senderName;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Hogy automatikusan beállítsa a dátumot mentéskor
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}