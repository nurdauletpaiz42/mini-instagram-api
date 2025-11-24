package com.miniinsta.api.repository;

import com.miniinsta.api.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Пока пустой, стандартных методов (save, findAll) нам хватит
}