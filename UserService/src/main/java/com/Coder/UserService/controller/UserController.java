package com.Coder.UserService.controller;

import com.Coder.UserService.dto.ApiResponse;
import com.Coder.UserService.dto.UserRegisterDTO;
import com.Coder.UserService.dto.UserResponseDTO;
import com.Coder.UserService.repository.UserRepository;
import com.Coder.UserService.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.html.HTMLHeadElement;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j

public class UserController {

    private final UserService userService;

    /**
     * Register a new user
     */

//    @GetMapping("{/id}")
//    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByID(@PathVariable("/id") UUID id){
//        UserResponseDTO userResponseDTO=userService.getUserByID(id);
//        return ResponseEntity.status()
//    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable("id") UUID id) {
        try {
            log.info("Fetching user with id: {}", id);

            UserResponseDTO userResponse = userService.getUserByID(id);

            return ResponseEntity.ok(
                    ApiResponse.success(HttpStatus.OK.value(), "User fetched successfully", userResponse)
            );

        } catch (RuntimeException e) {
            log.error("Error fetching user with id {}: {}", id, e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/AllUsers")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        try {
            log.info("Fetching all users");

            List<UserResponseDTO> users = userService.getAllUsers();

            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(ApiResponse.success(HttpStatus.NO_CONTENT.value(), "No users found", users));
            }

            return ResponseEntity.ok(
                    ApiResponse.success(HttpStatus.OK.value(), "Users fetched successfully", users)
            );

        } catch (Exception e) {
            log.error("Error fetching all users: ", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to fetch users"));
        }
    }


}