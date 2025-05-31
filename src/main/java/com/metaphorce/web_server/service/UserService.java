package com.metaphorce.web_server.service;

import org.springframework.stereotype.Service;

import com.metaphorce.web_server.model.User;
import com.metaphorce.web_server.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
