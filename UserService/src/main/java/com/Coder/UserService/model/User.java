package com.Coder.UserService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Authentication fields
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    // Profile fields
    @Column(name = "first_name", nullable = false, length = 100)
    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;

    @Column(length = 20)
    @Pattern(regexp = "^[\\+]?[1-9][\\d]{0,15}$", message = "Phone number must be valid")
    private String phone;

    @Column(name = "address_line1")
    @Size(max = 255, message = "Address line 1 must not exceed 255 characters")
    private String addressLine1;

    @Column(name = "address_line2")
    @Size(max = 255, message = "Address line 2 must not exceed 255 characters")
    private String addressLine2;

    @Column(length = 100)
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Column(length = 100)
    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @Column(name = "postal_code", length = 20)
    @Pattern(regexp = "^[A-Za-z0-9\\s\\-]{3,20}$", message = "Postal code must be valid")
    private String postalCode;

    @Column(length = 100)
    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @Column(name = "farm_name")
    @Size(max = 255, message = "Farm name must not exceed 255 characters")
    private String farmName; // for farmers

    @Column(name = "business_license")
    @Size(max = 255, message = "Business license must not exceed 255 characters")
    private String businessLicense; // for dealers

    // Relationships
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();;

    // Timestamps - using CreationTimestamp and UpdateTimestamp for consistency
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Helper method
    public String getFullName() {
        return firstName + " " + lastName;
    }
}