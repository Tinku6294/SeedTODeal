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
public class UserResponseDTO {

    private UUID id;
    private String email;
    private UserType userType;
    private UserStatus status;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String farmName;
    private String businessLicense;

}