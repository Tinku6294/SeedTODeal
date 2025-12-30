package com.Coder.UserService.mapper;

import com.Coder.UserService.dto.UserRegisterDTO;
import com.Coder.UserService.dto.UserResponseDTO;
import com.Coder.UserService.model.User;
import com.Coder.UserService.model.UserStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Maps UserRegisterDTO to User entity
     */
    public User toEntity(UserRegisterDTO dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                // Authentication fields
                .email(dto.getEmail())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .userType(dto.getUserType())
                .status(UserStatus.ACTIVE) // Default status

                // Profile fields
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .addressLine1(dto.getAddressLine1())
                .addressLine2(dto.getAddressLine2())
                .city(dto.getCity())
                .state(dto.getState())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .farmName(dto.getFarmName())
                .businessLicense(dto.getBusinessLicense())
                .build();
    }

    /**
     * Maps User entity to UserResponseDTO
     */
    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userType(user.getUserType())
                .status(user.getStatus())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .addressLine1(user.getAddressLine1())
                .addressLine2(user.getAddressLine2())
                .city(user.getCity())
                .state(user.getState())
                .postalCode(user.getPostalCode())
                .country(user.getCountry())
                .farmName(user.getFarmName())
                .businessLicense(user.getBusinessLicense())

                .build();
    }

}