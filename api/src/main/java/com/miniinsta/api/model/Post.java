package com.miniinsta.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK 

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false) // FK users.id 
    private User author;

    @Column(columnDefinition = "TEXT")
    private String caption; // TEXT 

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now(); // DATETIME 
}