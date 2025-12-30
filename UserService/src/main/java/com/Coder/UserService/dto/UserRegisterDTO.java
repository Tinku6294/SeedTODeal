package com.Coder.UserService.dto;

import com.Coder.UserService.model.UserType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDTO {

    // Authentication fields
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "User type is required")
    private UserType userType;

    // Profile fields
    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;

    @Pattern(regexp = "^[\\+]?[1-9][\\d]{0,15}$", message = "Phone number must be valid")
    private String phone;

    // Address fields (optional)
    @Size(max = 255, message = "Address line 1 must not exceed 255 characters")
    private String addressLine1;

    @Size(max = 255, message = "Address line 2 must not exceed 255 characters")
    private String addressLine2;

    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @Pattern(regexp = "^[A-Za-z0-9\\s\\-]{3,20}$", message = "Postal code must be valid")
    private String postalCode;

    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    private Set<String> roles; // <-- NEW
    // Optional fields based on user type
    @Size(max = 255, message = "Farm name must not exceed 255 characters")
    private String farmName; // for farmers

    @Size(max = 255, message = "Business license must not exceed 255 characters")
    private String businessLicense; // for dealers
}