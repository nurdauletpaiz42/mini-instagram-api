package com.miniinsta.api.dto;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String access_token;
    private String token_type;
}