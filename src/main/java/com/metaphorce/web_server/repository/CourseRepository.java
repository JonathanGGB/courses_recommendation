package com.metaphorce.web_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metaphorce.web_server.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {}