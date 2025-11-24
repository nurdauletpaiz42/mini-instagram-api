package com.miniinsta.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "follows", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"follower_id", "followee_id"}) // Нельзя подписаться дважды
})
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false) // Кто подписывается
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followee_id", nullable = false) // На кого подписывается
    private User followee;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}