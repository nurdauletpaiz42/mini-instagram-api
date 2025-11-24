package com.miniinsta.api.repository;

import com.miniinsta.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Эти методы Spring реализует сам, просто по названию!
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByUsername(String username);
}