package com.topia.user_management.service;

import com.topia.user_management.entity.User;
import com.topia.user_management.models.UserRequestDTO;
import com.topia.user_management.models.UserResponseDTO;
import com.topia.user_management.repository.UserRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final S3Client s3Client;

    public UserServiceImpl(UserRepository userRepository, S3Client s3Client) {
        this.userRepository = userRepository;
        this.s3Client = s3Client;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user ->
                        new UserResponseDTO(user.getId(), user.getName(), user.getAge())).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            User presentUser = user.get();
            return new UserResponseDTO(presentUser.getId(), presentUser.getName(), presentUser.getAge());
        }
        return null;
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User(userRequestDTO.getName(), userRequestDTO.getAge());
        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getAge());
    }

    @Override
    public UserResponseDTO updateUserById(Long id, UserRequestDTO userRequestDTO) {
        Optional<User> userDetails = userRepository.findById(id);
        if (userDetails.isPresent()){
            User existingUser = userDetails.get();
            existingUser.setName(userRequestDTO.getName());
            existingUser.setAge(userRequestDTO.getAge());
            User updatedUser = userRepository.save(existingUser);
            return new UserResponseDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getAge());
        }
        return createUser(userRequestDTO);
    }

}
