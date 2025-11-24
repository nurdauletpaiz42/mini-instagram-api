package com.miniinsta.api.controller;

import com.miniinsta.api.dto.LoginRequest;
import com.miniinsta.api.dto.LoginResponse;
import com.miniinsta.api.dto.RegisterRequest;
import com.miniinsta.api.model.User;
import com.miniinsta.api.repository.UserRepository;
import com.miniinsta.api.security.JwtCore; // Наш новый класс
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtCore jwtCore; // Подключаем генератор

    // --- РЕГИСТРАЦИЯ ---
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(409).body("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(409).body("Error: Email is already in use!");
        }
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPasswordHash(request.getPassword()); 
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    // --- ВХОД (ЛОГИН) - НОВОЕ! ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Ищем юзера по имени
        User user = userRepository.findByUsername(request.getUsername());
        
        // 2. Проверяем пароль (простое сравнение, так как шифрования пока нет)
        // Если юзера нет ИЛИ пароль не совпал -> Ошибка 401
        if (user == null || !user.getPasswordHash().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        // 3. Генерируем токен
        String token = jwtCore.generateToken(user.getUsername(), user.getId());

        // 4. Возвращаем JSON как просил учитель [cite: 53]
        return ResponseEntity.ok(new LoginResponse(token, "Bearer"));
    }
}