package com.teguhs.eventra.controller;

import com.teguhs.eventra.common.ApiResponse;
import com.teguhs.eventra.model.User;
import com.teguhs.eventra.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // fetch all users data
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> response = new ApiResponse<>(
                true,
                "Data fetched successfully",
                users
        );
        return ResponseEntity.ok(response);
    }

    // fetch user data by id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Save user data
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<User>> saveUser(@Valid @RequestBody User user) {
        User newUser = userService.createUser(user);
        ApiResponse<User> response = new ApiResponse<>(
                true,
                "Data added successfully",
                newUser
        );
        return ResponseEntity.ok(response);
    }

    // update user data by ID
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }
}
