package com.Coder.UserService.dto;

import com.Coder.UserService.model.UserStatus;
import com.Coder.UserService.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {

    private UUID id;
    
    // Essential profile information
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    
    // User classification
    private UserType userType;
    
    // Address information
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    
    // Business-specific fields (only shown when relevant)
    private String farmName;        // For farmers only
    private String businessLicense; // For dealers only
    
    // Account creation date (useful for profile display)
    private LocalDateTime createdAt;
    
    // Utility method
    public String getFullName() {
        return firstName + " " + lastName;
    }
}