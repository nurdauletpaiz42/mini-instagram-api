package com.miniinsta.api.dto;

import lombok.Data;

@Data
public class PostCreateRequest {
    private Long authorId; // Кто пишет пост (пока передаем вручную)
    private String caption; // Текст поста
}