package com.metaphorce.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metaphorce.user_service.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {}