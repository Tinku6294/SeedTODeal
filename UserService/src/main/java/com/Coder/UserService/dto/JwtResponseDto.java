package com.Coder.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDto {
    
    private String token;
    private String type = "Bearer";
    private LocalDateTime expiry;
    private String username;
    private String email;
    private String role;

    

    
    @Override
    public String toString() {
        return "JwtResponseDto{" +
                "type='" + type + '\'' +
                ", expiry=" + expiry +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}