package com.metaphorce.web_server.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metaphorce.dto.UserDTO;
import com.metaphorce.web_server.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("User created with id: " + createdUser.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully with ID: " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok("User updated successfully with ID: " + updatedUser.getId());
    }

    @PostMapping("/{userId}/courses/{courseId}")
    public ResponseEntity<UserDTO> addCourseToUser(@PathVariable Long userId, @PathVariable Long courseId) {
        UserDTO updatedUser = userService.addCourseToUser(userId, courseId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}/courses/{courseId}")
    public ResponseEntity<UserDTO> removeCourseFromUser(@PathVariable Long userId, @PathVariable Long courseId) {
        UserDTO updatedUser = userService.removeCourseFromUser(userId, courseId);
        return ResponseEntity.ok(updatedUser);
    }
}