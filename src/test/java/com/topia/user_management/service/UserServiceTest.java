package com.topia.user_management.service;

import com.topia.user_management.entity.User;
import com.topia.user_management.models.UserRequestDTO;
import com.topia.user_management.models.UserResponseDTO;
import com.topia.user_management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setName("Akash Sharma");
        userDTO.setAge(29);

        User user = new User();
        user.setName(userDTO.getName());
        user.setAge(29);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO createdUser = userService.createUser(userDTO);

        assertEquals("Akash Sharma", createdUser.getName());
        assertEquals(29, createdUser.getAge());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setName("Akash");
        user.setAge(29);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponseDTO retrievedUser = userService.getUserById(userId);

        assertEquals("Akash", retrievedUser.getName());
        assertEquals(29, retrievedUser.getAge());
    }


    @Test
    public void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setName("Akash");
        user1.setAge(29);

        User user2 = new User();
        user2.setName("Abhishek");
        user2.setAge(32);

        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<UserResponseDTO> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("Akash", users.get(0).getName());
        assertEquals(29, users.get(0).getAge());
        assertEquals("Abhishek", users.get(1).getName());
        assertEquals(32, users.get(1).getAge());
    }


    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Akash");
        userRequestDTO.setAge(29);

        User existingUser = new User();
        existingUser.setName("Amit");
        existingUser.setAge(21);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        UserResponseDTO updatedUser = userService.updateUserById(userId, userRequestDTO);

        assertEquals("Akash", updatedUser.getName());
        assertEquals(29, updatedUser.getAge());
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);

        boolean result = userService.deleteUserById(userId);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUser_NotFound() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        boolean result = userService.deleteUserById(userId);

        assertFalse(result);
        verify(userRepository, times(0)).deleteById(userId);
    }

}
