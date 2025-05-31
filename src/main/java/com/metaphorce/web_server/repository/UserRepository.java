package com.metaphorce.web_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metaphorce.web_server.model.User;

public interface UserRepository extends JpaRepository<User, Long> {}