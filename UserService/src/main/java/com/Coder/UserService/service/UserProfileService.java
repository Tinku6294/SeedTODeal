package com.Coder.UserService.service;

import com.Coder.UserService.dto.UserProfileDTO;
import com.Coder.UserService.exception.UserNotFoundException;
import com.Coder.UserService.mapper.UserMapper;
import com.Coder.UserService.mapper.UserProfileMapper;
import com.Coder.UserService.model.User;
import com.Coder.UserService.model.UserType;
import com.Coder.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileMapper userMapper;

    /**
     * Retrieves user profile by user ID
     *
     * @param userId the user ID
     * @return UserProfileDTO
     * @throws UserNotFoundException if user not found
     */
    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile(UUID userId) {
        log.debug("Fetching profile for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        UserProfileDTO profileDTO = userMapper.toDTO(user);
        log.debug("Successfully retrieved profile for user: {}", user.getEmail());

        return profileDTO;
    }

    /**
     * Retrieves user profile by email
     *
     * @param email the user email
     * @return UserProfileDTO
     * @throws UserNotFoundException if user not found
     */
    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfileByEmail(String email) {
        log.debug("Fetching profile for user email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        UserProfileDTO profileDTO = userMapper.toDTO(user);
        log.debug("Successfully retrieved profile for user: {}", email);

        return profileDTO;
    }

    /**
     * Updates user profile
     *
     * @param userId the user ID
     * @param profileDTO the updated profile data
     * @return updated UserProfileDTO
     * @throws UserNotFoundException if user not found
     */
    public UserProfileDTO updateUserProfile(UUID userId, UserProfileDTO profileDTO) {
        log.debug("Updating profile for user ID: {}", userId);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        // Validate business-specific fields based on user type
        validateBusinessFields(existingUser.getUserType(), profileDTO);

        // Update the entity
        userMapper.updateEntityFromProfileDTO(existingUser, profileDTO);

        // Save and return updated profile
        User savedUser = userRepository.save(existingUser);
        UserProfileDTO updatedProfile = userMapper.toDTO(savedUser);

        log.info("Successfully updated profile for user: {}", existingUser.getEmail());

        return updatedProfile;
    }

    /**
     * Updates specific profile fields (partial update)
     *
     * @param userId the user ID
     * @param profileDTO the profile data with fields to update
     * @return updated UserProfileDTO
     * @throws UserNotFoundException if user not found
     */
    public UserProfileDTO updateProfileFields(UUID userId, UserProfileDTO profileDTO) {
        log.debug("Partially updating profile for user ID: {}", userId);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        // Only update non-null fields
        updateNonNullFields(existingUser, profileDTO);

        User savedUser = userRepository.save(existingUser);
        UserProfileDTO updatedProfile = userMapper.toDTO(savedUser);

        log.info("Successfully partially updated profile for user: {}", existingUser.getEmail());

        return updatedProfile;
    }

    /**
     * Checks if user profile is complete
     *
     * @param userId the user ID
     * @return true if profile is complete, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean isProfileComplete(UUID userId) {
        log.debug("Checking profile completeness for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        UserProfileDTO profile = userMapper.toDTO(user);

        // Check required fields
        boolean isComplete = profile.getFirstName() != null && !profile.getFirstName().trim().isEmpty() &&
                           profile.getLastName() != null && !profile.getLastName().trim().isEmpty() &&
                           profile.getPhone() != null && !profile.getPhone().trim().isEmpty() &&
                           profile.getAddressLine1() != null && !profile.getAddressLine1().trim().isEmpty() &&
                           profile.getCity() != null && !profile.getCity().trim().isEmpty() &&
                           profile.getState() != null && !profile.getState().trim().isEmpty() &&
                           profile.getCountry() != null && !profile.getCountry().trim().isEmpty();

        // Additional checks for business users
        if (user.getUserType() == UserType.FARMER) {
            isComplete = isComplete && profile.getFarmName() != null && !profile.getFarmName().trim().isEmpty();
        } else if (user.getUserType() == UserType.DEALER) {
            isComplete = isComplete && profile.getBusinessLicense() != null && !profile.getBusinessLicense().trim().isEmpty();
        }

        log.debug("Profile completeness for user {}: {}", user.getEmail(), isComplete);

        return isComplete;
    }

    /**
     * Validates business-specific fields based on user type
     */
    private void validateBusinessFields(UserType userType, UserProfileDTO profileDTO) {
        if (userType == UserType.FARMER) {
            // Clear business license for farmers
            profileDTO.setBusinessLicense(null);
        } else if (userType == UserType.DEALER) {
            // Clear farm name for dealers
            profileDTO.setFarmName(null);
        } else {
            // Clear both for regular users
            profileDTO.setFarmName(null);
            profileDTO.setBusinessLicense(null);
        }
    }

    /**
     * Updates only non-null fields from profileDTO to existingUser
     */
    private void updateNonNullFields(User existingUser, UserProfileDTO profileDTO) {
        if (profileDTO.getFirstName() != null) {
            existingUser.setFirstName(profileDTO.getFirstName());
        }
        if (profileDTO.getLastName() != null) {
            existingUser.setLastName(profileDTO.getLastName());
        }
        if (profileDTO.getPhone() != null) {
            existingUser.setPhone(profileDTO.getPhone());
        }
        if (profileDTO.getAddressLine1() != null) {
            existingUser.setAddressLine1(profileDTO.getAddressLine1());
        }
        if (profileDTO.getAddressLine2() != null) {
            existingUser.setAddressLine2(profileDTO.getAddressLine2());
        }
        if (profileDTO.getCity() != null) {
            existingUser.setCity(profileDTO.getCity());
        }
        if (profileDTO.getState() != null) {
            existingUser.setState(profileDTO.getState());
        }
        if (profileDTO.getPostalCode() != null) {
            existingUser.setPostalCode(profileDTO.getPostalCode());
        }
        if (profileDTO.getCountry() != null) {
            existingUser.setCountry(profileDTO.getCountry());
        }

        // Handle business-specific fields based on user type
        if (existingUser.getUserType() == UserType.FARMER && profileDTO.getFarmName() != null) {
            existingUser.setFarmName(profileDTO.getFarmName());
        }
        if (existingUser.getUserType() == UserType.DEALER && profileDTO.getBusinessLicense() != null) {
            existingUser.setBusinessLicense(profileDTO.getBusinessLicense());
        }
    }
}