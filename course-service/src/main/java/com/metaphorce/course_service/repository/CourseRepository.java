package com.metaphorce.course_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metaphorce.course_service.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {}