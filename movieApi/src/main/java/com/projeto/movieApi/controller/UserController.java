package com.projeto.movieApi.controller;

import com.projeto.movieApi.dto.UserDTO;
import com.projeto.movieApi.dto.UserResponseDTO;
import com.projeto.movieApi.model.User;
import com.projeto.movieApi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllUser() {

        List<User> userList = userService.findAll();

        List<UserResponseDTO> responseDTOList = userList.stream().map(
                user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail())
        ).toList();

        return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);

    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createdUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userService.createdUser(userDTO);

        UserResponseDTO response = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO)  {

        User user = userService.userUpdate(userDTO, id);

        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
