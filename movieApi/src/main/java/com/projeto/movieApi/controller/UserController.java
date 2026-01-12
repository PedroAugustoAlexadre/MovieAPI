package com.projeto.movieApi.controller;

import com.projeto.movieApi.dto.UserDTO;
import com.projeto.movieApi.model.User;
import com.projeto.movieApi.repository.UserRepository;
import com.projeto.movieApi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "users")
public class UserController {

    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createdUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userService.createdUser(userDTO);

        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
