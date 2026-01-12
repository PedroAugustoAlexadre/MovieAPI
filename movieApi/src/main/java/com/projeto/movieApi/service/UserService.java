package com.projeto.movieApi.service;

import com.projeto.movieApi.dto.UserDTO;
import com.projeto.movieApi.model.User;
import com.projeto.movieApi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Transactional
    public User createdUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.username())) {
            throw new RuntimeException("Username already taken!");
        }
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new RuntimeException("Email already registered!");
        }

        User user = new User();
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());

        return userRepository.save(user);
    }

    @Transactional
    public User userUpdate(UserDTO userDTO, Long id) {
        User user = this.findById(id);

        userRepository.findByUsername(userDTO.username()).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(id)) {
                throw new RuntimeException("This username is already taken by another user!");
            }
        });

        userRepository.findByEmail(userDTO.email()).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(id)) {
                throw new RuntimeException("This email is already registered by another user!");
            }
        });

        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = this.findById(id);
        userRepository.delete(user);
    }
}