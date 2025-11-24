package com.miniinsta.api.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponse {
    private Long id;
    private String caption;
    private LocalDateTime createdAt;
    private AuthorDto author;
    private PostStatsDto stats;
    private PostViewerDto viewer;

    // Внутренние классы (чтобы все было в одном месте)
    
    @Data
    public static class AuthorDto {
        private Long id;
        private String username;
        private String avatarUrl;
    }

    @Data
    public static class PostStatsDto {
        private int likesCount;
        private int commentsCount;
    }

    @Data
    public static class PostViewerDto {
        private boolean likedByMe;
    }
}