package com.Coder.UserService.service;

import com.Coder.UserService.dto.UserRegisterDTO;
import com.Coder.UserService.dto.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO registerUser(UserRegisterDTO registerDTO);

    UserResponseDTO getUserByID(UUID uuid);

    List<UserResponseDTO> getAllUsers();
}
