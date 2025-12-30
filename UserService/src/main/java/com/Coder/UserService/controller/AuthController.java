package com.Coder.UserService.controller;

import com.Coder.UserService.dto.*;

import com.Coder.UserService.service.UserService;
import com.Coder.UserService.utill.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(@Valid @RequestBody UserRegisterDTO registerDTO) {
        try {
            log.info("Received registration request for email: {}", registerDTO.getEmail());

            UserResponseDTO responseDTO = userService.registerUser(registerDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(HttpStatus.CREATED.value(), "User registered successfully", responseDTO));

        } catch (Exception e) {
            log.error("Error during user registration: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponseDto>> login(@Valid @RequestBody LoginDto loginDto) {
        JwtResponseDto jwtResponse = authService.login(loginDto);
        return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "Login successful", jwtResponse)
        );
    }
}
