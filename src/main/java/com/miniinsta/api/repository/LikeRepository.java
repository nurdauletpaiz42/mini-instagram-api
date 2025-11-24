package com.miniinsta.api.repository;

import com.miniinsta.api.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    
    // Подсчитать количество лайков у конкретного поста
    int countByPostId(Long postId);

    // Проверить, лайкнул ли конкретный юзер конкретный пост
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    // Найти конкретный лайк (чтобы удалить его, если юзер передумал)
    Like findByUserIdAndPostId(Long userId, Long postId);
}