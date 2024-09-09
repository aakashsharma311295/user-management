package com.topia.user_management.controller;

import com.topia.user_management.models.UserRequestDTO;
import com.topia.user_management.models.UserResponseDTO;
import com.topia.user_management.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO user = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable("id") Long id, @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO user = userService.updateUserById(id, userRequestDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        boolean userDeleted = userService.deleteUserById(id);
        if (userDeleted){
            return ResponseEntity.ok("user deleted successfully !!");
        }else {
            return ResponseEntity.ok("user not present !!");
        }
    }
}
