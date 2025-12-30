package com.Coder.UserService.service;

import com.Coder.UserService.dto.UserRegisterDTO;
import com.Coder.UserService.dto.UserResponseDTO;
import com.Coder.UserService.exception.UserAlreadyExistsException;
import com.Coder.UserService.mapper.UserMapper;
import com.Coder.UserService.model.Role;
import com.Coder.UserService.model.User;
import com.Coder.UserService.repository.RoleRepository;
import com.Coder.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j

public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    @Override
    public UserResponseDTO registerUser(UserRegisterDTO registerDTO) {
        log.info("Attempting to register user with email: {}", registerDTO.getEmail());

        // Check if user already exists
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            log.warn("User with email {} already exists", registerDTO.getEmail());
            throw new UserAlreadyExistsException("User with email " + registerDTO.getEmail() + " already exists");
        }

        // Map DTO to entity
        User user = userMapper.toEntity(registerDTO);
        Set<Role> assignedRoles =registerDTO.getRoles().stream()
                .map(roleName -> roleRepository.findByName((roleName.toUpperCase()))
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        user.setRoles(assignedRoles);


        // Save user
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());

        // Return response DTO
        return userMapper.toResponseDTO(savedUser);
    }
   @Override
   public UserResponseDTO getUserByID(UUID uuid) {
        return userRepository.findById(uuid)
                .map(UserMapper::toResponseDTO) // convert User → UserResponseDTO
                .orElseThrow(() -> new RuntimeException("User not found with id: " + uuid));
    }
    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDTO) // Convert each User → UserResponseDTO
                .toList();
    }


}
