package com.topia.user_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topia.user_management.models.UserRequestDTO;
import com.topia.user_management.models.UserResponseDTO;
import com.topia.user_management.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserResponseDTO userResponseDTO;

    private UserRequestDTO userRequestDTO;

    @BeforeEach
    public void setup() {
        userResponseDTO = new UserResponseDTO(1L, "Akash", 29);

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("Akash");
        userRequestDTO.setAge(29);
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userService.createUser(any(UserRequestDTO.class))).thenReturn(userResponseDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(jsonPath("$.id").value(userResponseDTO.getId()))
                .andExpect(jsonPath("$.name").value(userResponseDTO.getName()))
                .andExpect(jsonPath("$.age").value(userResponseDTO.getAge()));

        verify(userService, times(1)).createUser(any(UserRequestDTO.class));
    }
}
