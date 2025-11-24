package com.miniinsta.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Data // Lombok сам создаст геттеры и сеттеры
@Entity
@Table(name = "users") // Название таблицы в БД
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK 

    @Column(nullable = false, unique = true, length = 32)
    private String username; // VARCHAR(32) UNIQUE 

    @Column(nullable = false, unique = true)
    private String email; // VARCHAR(255) UNIQUE 

    @Column(name = "password_hash", nullable = false)
    private String passwordHash; // VARCHAR(255) 

    @Column(columnDefinition = "TEXT")
    private String bio; // TEXT 

    @Column(name = "avatar_url", length = 512)
    private String avatarUrl; // VARCHAR(512) 

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now(); // DATETIME 
}