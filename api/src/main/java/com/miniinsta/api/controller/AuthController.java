package com.miniinsta.api.controller;

import com.miniinsta.api.dto.RegisterRequest;
import com.miniinsta.api.model.User;
import com.miniinsta.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // 1. Проверяем, не занят ли логин или почта (Требование учителя: 409 Conflict)
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(409).body("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(409).body("Error: Email is already in use!");
        }

        // 2. Создаем нового пользователя
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        // Пока сохраняем пароль как есть (шифрование добавим позже, чтобы не усложнять сейчас)
        newUser.setPasswordHash(request.getPassword()); 

        // 3. Сохраняем в базу
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }
}