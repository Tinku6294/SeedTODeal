package com.Coder.UserService.mapper;

import com.Coder.UserService.dto.UserProfileDTO;
import com.Coder.UserService.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    /**
     * Maps User entity to UserProfileDTO
     *
     * @param user the User entity
     * @return UserProfileDTO
     */
    public UserProfileDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserProfileDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .userType(user.getUserType())
                .addressLine1(user.getAddressLine1())
                .addressLine2(user.getAddressLine2())
                .city(user.getCity())
                .state(user.getState())
                .postalCode(user.getPostalCode())
                .country(user.getCountry())
                .farmName(user.getFarmName())
                .businessLicense(user.getBusinessLicense())
                .createdAt(user.getCreatedAt())
                .build();
    }

    /**
     * Maps UserProfileDTO to User entity
     * Note: This is for update operations, doesn't map ID, timestamps, or auth fields
     *
     * @param userProfileDTO the UserProfileDTO
     * @return User entity
     */
    public User toEntity(UserProfileDTO userProfileDTO) {
        if (userProfileDTO == null) {
            return null;
        }

        return User.builder()
                .firstName(userProfileDTO.getFirstName())
                .lastName(userProfileDTO.getLastName())
                .phone(userProfileDTO.getPhone())
                .addressLine1(userProfileDTO.getAddressLine1())
                .addressLine2(userProfileDTO.getAddressLine2())
                .city(userProfileDTO.getCity())
                .state(userProfileDTO.getState())
                .postalCode(userProfileDTO.getPostalCode())
                .country(userProfileDTO.getCountry())
                .farmName(userProfileDTO.getFarmName())
                .businessLicense(userProfileDTO.getBusinessLicense())
                .build();
    }

    /**
     * Updates existing User entity with data from UserProfileDTO
     * Preserves ID, email, password, user type, status, roles, and timestamps
     *
     * @param existingUser the existing User entity
     * @param userProfileDTO the UserProfileDTO with updated data
     */
    public void updateEntityFromProfileDTO(User existingUser, UserProfileDTO userProfileDTO) {
        if (existingUser == null || userProfileDTO == null) {
            return;
        }

        existingUser.setFirstName(userProfileDTO.getFirstName());
        existingUser.setLastName(userProfileDTO.getLastName());
        existingUser.setEmail(userProfileDTO.getEmail());
        existingUser.setPhone(userProfileDTO.getPhone());
        existingUser.setAddressLine1(userProfileDTO.getAddressLine1());
        existingUser.setAddressLine2(userProfileDTO.getAddressLine2());
        existingUser.setCity(userProfileDTO.getCity());
        existingUser.setState(userProfileDTO.getState());
        existingUser.setPostalCode(userProfileDTO.getPostalCode());
        existingUser.setCountry(userProfileDTO.getCountry());
        existingUser.setFarmName(userProfileDTO.getFarmName());
        existingUser.setBusinessLicense(userProfileDTO.getBusinessLicense());
    }
}