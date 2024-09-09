package com.topia.user_management.service;

import com.topia.user_management.models.UserRequestDTO;
import com.topia.user_management.models.UserResponseDTO;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    boolean deleteUserById(Long id);

    UserResponseDTO createUser(UserRequestDTO user);

    UserResponseDTO updateUserById(Long id, UserRequestDTO user);
}
