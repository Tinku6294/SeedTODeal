package com.Coder.UserService.controller;

import com.Coder.UserService.dto.ApiResponse;
import com.Coder.UserService.dto.UserProfileDTO;
import com.Coder.UserService.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
@Slf4j
public class UserProfileController {

    private final UserProfileService profileService;

    @Autowired
    public UserProfileController(UserProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getProfile(@PathVariable UUID userId) {
        try {
            log.info("Fetching profile for userId: {}", userId);
            UserProfileDTO profile = profileService.getUserProfile(userId);

            return ResponseEntity.ok(
                    ApiResponse.success(HttpStatus.OK.value(), "Profile fetched successfully", profile)
            );
        } catch (Exception e) {
            log.error("Error fetching profile for userId {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileDTO>> updateProfile(
            @PathVariable UUID userId,
            @RequestBody UserProfileDTO profileDTO) {
        try {
            log.info("Updating profile for userId: {}", userId);
            UserProfileDTO updatedProfile = profileService.updateUserProfile(userId, profileDTO);

            return ResponseEntity.ok(
                    ApiResponse.success(HttpStatus.OK.value(), "Profile updated successfully", updatedProfile)
            );
        } catch (Exception e) {
            log.error("Error updating profile for userId {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileDTO>> partialUpdate(
            @PathVariable UUID userId,
            @RequestBody UserProfileDTO profileDTO) {
        try {
            log.info("Partially updating profile for userId: {}", userId);
            UserProfileDTO updatedProfile = profileService.updateProfileFields(userId, profileDTO);

            return ResponseEntity.ok(
                    ApiResponse.success(HttpStatus.OK.value(), "Profile fields updated successfully", updatedProfile)
            );
        } catch (Exception e) {
            log.error("Error partially updating profile for userId {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping("/{userId}/complete")
    public ResponseEntity<ApiResponse<Boolean>> isProfileComplete(@PathVariable UUID userId) {
        try {
            log.info("Checking profile completeness for userId: {}", userId);
            boolean complete = profileService.isProfileComplete(userId);

            return ResponseEntity.ok(
                    ApiResponse.success(HttpStatus.OK.value(),
                            complete ? "Profile is complete" : "Profile is incomplete",
                            complete)
            );
        } catch (Exception e) {
            log.error("Error checking profile completeness for userId {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }
}
