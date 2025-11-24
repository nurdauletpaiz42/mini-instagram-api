package com.miniinsta.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false) // Связь с постом
    private Post post;

    @Column(length = 512)
    private String url;

    @Column(name = "mime_type", length = 64)
    private String mimeType; // например "image/jpeg"
}