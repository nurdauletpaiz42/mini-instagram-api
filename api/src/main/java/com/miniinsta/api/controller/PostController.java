package com.miniinsta.api.controller;

import com.miniinsta.api.dto.PostCreateRequest;
import com.miniinsta.api.dto.PostResponse;
import com.miniinsta.api.model.Like;
import com.miniinsta.api.model.Post;
import com.miniinsta.api.model.User;
import com.miniinsta.api.repository.LikeRepository; // Импорт
import com.miniinsta.api.repository.PostRepository;
import com.miniinsta.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository; // Подключили репозиторий лайков

    // --- СОЗДАНИЕ ПОСТА ---
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostCreateRequest request) {
        User author = userRepository.findById(request.getAuthorId()).orElse(null);
        if (author == null) return ResponseEntity.status(404).body("User not found");

        Post newPost = new Post();
        newPost.setCaption(request.getCaption());
        newPost.setAuthor(author);
        newPost.setCreatedAt(LocalDateTime.now());
        postRepository.save(newPost);

        return ResponseEntity.status(201).body("Post created successfully!");
    }

    // --- ПОСТАВИТЬ / УБРАТЬ ЛАЙК ---
    @PostMapping("/{postId}/like")
    public ResponseEntity<?> toggleLike(@PathVariable Long postId) {
        // ХАК: Пока у нас нет авторизации, считаем, что лайкает юзер с ID = 1
        Long currentUserId = 1L; 

        User user = userRepository.findById(currentUserId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);

        if (user == null || post == null) {
            return ResponseEntity.status(404).body("User or Post not found");
        }

        // Проверяем: лайк уже стоит?
        Like existingLike = likeRepository.findByUserIdAndPostId(currentUserId, postId);

        if (existingLike != null) {
            // Если стоит -> удаляем (дизлайк)
            likeRepository.delete(existingLike);
            return ResponseEntity.ok("Like removed");
        } else {
            // Если нет -> ставим
            Like newLike = new Like();
            newLike.setUser(user);
            newLike.setPost(post);
            likeRepository.save(newLike);
            return ResponseEntity.ok("Like added");
        }
    }

    // --- ПОЛУЧЕНИЕ СПИСКА (С РЕАЛЬНЫМИ ЦИФРАМИ) ---
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        Long currentUserId = 1L; // Тот, кто смотрит ленту (пока хардкод)

        List<Post> posts = postRepository.findAll();

        List<PostResponse> response = posts.stream().map(post -> {
            PostResponse dto = new PostResponse();
            dto.setId(post.getId());
            dto.setCaption(post.getCaption());
            dto.setCreatedAt(post.getCreatedAt());

            // Автор
            PostResponse.AuthorDto authorDto = new PostResponse.AuthorDto();
            authorDto.setId(post.getAuthor().getId());
            authorDto.setUsername(post.getAuthor().getUsername());
            authorDto.setAvatarUrl(post.getAuthor().getAvatarUrl());
            dto.setAuthor(authorDto);

            // Статистика (ТЕПЕРЬ РЕАЛЬНАЯ!)
            PostResponse.PostStatsDto statsDto = new PostResponse.PostStatsDto();
            // Спрашиваем у базы количество лайков
            statsDto.setLikesCount(likeRepository.countByPostId(post.getId())); 
            statsDto.setCommentsCount(0); // Комменты пока 0
            dto.setStats(statsDto);

            // Viewer (ТЕПЕРЬ РЕАЛЬНЫЙ!)
            PostResponse.PostViewerDto viewerDto = new PostResponse.PostViewerDto();
            // Спрашиваем у базы, лайкнул ли я
            viewerDto.setLikedByMe(likeRepository.existsByUserIdAndPostId(currentUserId, post.getId()));
            dto.setViewer(viewerDto);

            return dto;
        }).toList();

        return ResponseEntity.ok(response);
    }
}