package com.Coder.UserService.controller;

import com.Coder.UserService.dto.ApiResponse;
import com.Coder.UserService.dto.UserResponseDTO;
import com.Coder.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/farmer")
@RequiredArgsConstructor
@Slf4j
public class FarmerController {
    @Autowired
    private UserService userService;
    @PreAuthorize("hasAuthority('FARMER')")
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
